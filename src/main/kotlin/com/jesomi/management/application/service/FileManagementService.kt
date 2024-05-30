package com.jesomi.management.application.service

import com.jesomi.management.application.model.FileDto

interface FileManagementService {
    /**
     * 파일 업로드 목록 조회
     */
    fun findAllFileUploadByFileManageCd(fileManageCd: String, page: Int, size: Int): FileDto.FileUploadListResponse

    /**
     * pre-signed-url 생성 요청
     */
    fun generatePreSignedUrl(fileManageCd: String, fileName: String, regId: String): FileDto.FilePreSignedResponse

    /**
     * 파일 업로드 완료 처리
     */
    fun completeFileUpload(uploadId: String, reqId: String): FileDto.FileUploadCompleteResponse

    /**
     * 파일 업로드 삭제 처리
     * 물리 파일 삭제 및 DB 삭제
     */
    fun deleteFileUpload(fileSeq: Long, reqId: String)
}