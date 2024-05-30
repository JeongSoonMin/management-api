package com.jesomi.management.application.service.impl


import com.jesomi.management.application.exception.FileManagementException
import com.jesomi.management.application.model.CacheDto
import com.jesomi.management.application.model.FileDto
import com.jesomi.management.application.service.AmazonService
import com.jesomi.management.application.service.CacheService
import com.jesomi.management.application.service.FileManagementService
import com.jesomi.management.global.common.log
import com.jesomi.management.global.enums.ResponseCode
import com.jesomi.management.global.util.RandomUtil
import com.jesomi.management.persistence.rdb.repository.FileUploadRepository
import com.jesomi.management.persistence.redis.entity.FileUploadPrepare
import com.jesomi.management.persistence.redis.repository.FileUploadPrepareRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional(readOnly = true)
class FileManagementServiceImpl(
    private val fileUploadRepository: FileUploadRepository,
    private val fileUploadPrepareRepository: FileUploadPrepareRepository,
    private val amazonService: AmazonService,
    private val cacheService: CacheService
) : FileManagementService {
    val log = this.log()

    /**
     * 파일 업로드 목록 조회
     */
    override fun findAllFileUploadByFileManageCd(
        fileManageCd: String,
        page: Int,
        size: Int
    ): FileDto.FileUploadListResponse {
        // front에서는 페이지번호 1부터 시작하나 pageable에서는 페이지인덱스 0부터 시작하기 때문에 -1 처리
        val pageable = PageRequest.of(page - 1, size)
        return FileDto.FileUploadListResponse.of(
            fileUploadRepository.findByFileManageCdAndUsingJoinManage(
                fileManageCd,
                pageable
            )
        )
    }

    /**
     * pre-signed-url 생성 요청
     */
    @Transactional
    override fun generatePreSignedUrl(
        fileManageCd: String,
        fileName: String,
        regId: String
    ): FileDto.FilePreSignedResponse {
        val fileManage: CacheDto.FileManageCache = cacheService.findFileManageCache(fileManageCd)
            ?: throw FileManagementException(ResponseCode.NOT_SUPPORTED_UPLOAD_TYPE)

        var uploadFileName = fileName
        val now = LocalDate.now()
        var uploadPath = fileManage.uploadPath

        // 업로드 경로 치환 /년/월/일 경로 셋팅된 경우 치환 처리
        uploadPath = uploadPath.replace("{yyyy}", now.year.toString())
            .replace("{mm}", String.format("%02d", now.monthValue))
            .replace("{dd}", String.format("%02d", now.dayOfMonth))

        // 원본 파일명 사용하지 않을 경우 랜덤 16자리 알파벳,숫자 파일명 생성
        if (!"Y".equals(fileManage.originFileNameYn)) uploadFileName =
            RandomUtil.getRandomNumAlpha(16) + "." + fileName.substring(
                fileName.lastIndexOf(".") + 1,
                fileName.length
            )

        // 동일한 파일 있는지 체크
        if (amazonService.isS3FileExist(
                fileManage.bucketName,
                uploadPath + uploadFileName
            )
        ) throw FileManagementException(ResponseCode.FILE_MANAGEMENT_FILE_EXISTS)

        // redis 10분간만 유효 저장.
        val uploadId = UUID.randomUUID()
        fileUploadPrepareRepository.save(
            FileUploadPrepare(
                uploadId = uploadId,
                fileManageCd = fileManage.fileManageCd,
                bucketName = fileManage.bucketName,
                uploadPath = uploadPath,
                uploadFileName = uploadFileName,
                originFileName = fileName,
                regId = regId,
                regDate = LocalDateTime.now()
            )
        )

        val preSignedUrl =
            amazonService.s3GeneratePreSignedUrl(fileManage.bucketName, uploadPath + uploadFileName)

        return FileDto.FilePreSignedResponse(preSignedUrl, uploadId.toString())
    }

    /**
     * 파일 업로드 완료 처리
     */
    @Transactional
    override fun completeFileUpload(
        uploadId: String,
        reqId: String
    ): FileDto.FileUploadCompleteResponse {
        // redis 조회
        val fileUploadPrepare = fileUploadPrepareRepository.findById(uploadId)
            .orElseThrow { throw FileManagementException(ResponseCode.FILE_UPLOAD_PREPARED_NOT_FOUND) }

        val fileUpload = fileUploadPrepare.toCmsFileUploadEntity()

        // DB 저장
        fileUploadRepository.save(fileUpload)
        // 저장 완료 후 redis 삭제
        fileUploadPrepareRepository.delete(fileUploadPrepare)

        return FileDto.FileUploadCompleteResponse(fileUpload.fileManageCd, fileUpload.fileSeq)
    }

    /**
     * 파일 업로드 삭제 처리
     */
    @Transactional
    override fun deleteFileUpload(fileSeq: Long, reqId: String) {
        val fileUpload = fileUploadRepository.findById(fileSeq)
            .orElseThrow { throw FileManagementException(ResponseCode.FILE_UPLOAD_NOT_FOUND) }
            // 이미 삭제 된 파일 일 경우 Exception 처리
            .isDeletedThrow { throw FileManagementException(ResponseCode.FILE_UPLOAD_DELETED) }

        // s3 파일 삭제 처리
        amazonService.s3DeleteObject(
            fileUpload.bucketName,
            fileUpload.uploadPath + fileUpload.uploadFileName
        )
        // DB 삭제 처리
        fileUpload.delete(reqId)
    }

}