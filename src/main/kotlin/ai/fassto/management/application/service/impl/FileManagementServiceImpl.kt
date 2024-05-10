package ai.fassto.management.application.service.impl

import ai.fassto.management.application.exception.FileManagementException
import ai.fassto.management.application.service.FileManagementService
import ai.fassto.management.global.configuration.properties.S3Properties
import ai.fassto.management.global.enums.ResponseCode
import ai.fassto.management.global.util.RandomUtil
import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Slf4j
@Service
@Transactional(readOnly = true)
class FileManagementServiceImpl(
    val amazonS3Client: AmazonS3,
    val s3Properties: S3Properties
): FileManagementService {
    val logger: Logger = LoggerFactory.getLogger(FileManagementService::class.java)

    override fun generatePreSignedUrl(uploadType: String, fileName: String): String? {
        var bucketName: String? = null
        var uploadPath: String? = null

        if (uploadType == "event") {
            bucketName = s3Properties.cdnBucket.bucketName
            uploadPath = s3Properties.cdnBucket.uploadPath.event
        }

        if (bucketName == null || uploadPath == null) {
            throw FileManagementException(ResponseCode.NOT_SUPPORTED_UPLOAD_TYPE)
        }

        /*
        TODO DB 등록 처리?
        file_seq
        upload_type
        origin_file_name
        file_ext
        upload_file_name
        upload_related_path
        status
        reg_time
        reg_name
         */

        return generatePreSignedUrl(bucketName, uploadPath, fileName)
    }

    fun generatePreSignedUrl(bucketName: String, uploadPath: String, fileName: String): String? {
        val randomFileName = RandomUtil.getRandomNumAlpha(16)
        val fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)
        val uploadFullPath = "${uploadPath}${randomFileName}.${fileExt}"

        val expiration = Date()
        var expTimeMillis: Long = expiration.time
        expTimeMillis += (10 * 60 * 1000).toLong() // 10분
        expiration.time = expTimeMillis // url 만료 시간 설정

        val url = amazonS3Client.generatePresignedUrl(
            GeneratePresignedUrlRequest(bucketName, uploadFullPath)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration)
        )

        logger.info("upload path >>> {}", url)

        return url.toString()
    }

}