package com.jesomi.management.application.model

import com.jesomi.management.persistence.rdb.enums.FileUploadStatus
import com.jesomi.management.persistence.rdb.model.FileUploadData
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import org.springframework.data.domain.Page
import java.time.LocalDateTime

class FileDto {
    data class FilePreSignedResponse(
        @Schema(description = "업로드 s3 URL")
        val preSignedUrl: String,
        @Schema(description = "업로드 ID")
        val uploadId: String
    )

    data class FileUploadCompleteRequest(
        @Schema(description = "업로드 ID")
        @field:NotNull(message = "업로드 ID가 누락 되었습니다.")
        val uploadId: String?,
        @Schema(description = "요청자 ID")
        @field:NotNull(message = "요청자 ID가 누락 되었습니다.")
        val reqId: String?
    )

    data class FileUploadCompleteResponse(
        @Schema(description = "파일 관리 코드")
        val fileManageCd: String?,
        @Schema(description = "파일 일련번호")
        val fileSeq: Long?
    )

    data class FileUploadListResponse(
        @Schema(description = "샘플 목록")
        var content: List<FileUploadListData>,
        @Schema(description = "샘플 전체 수")
        var total: Long
    ) {
        companion object {
            fun of(list: Page<FileUploadData>): FileUploadListResponse {
                return FileUploadListResponse(
                    list.content.map { e ->
                        FileUploadListData(
                            e.fileSeq,
                            e.domainName + e.uploadPath + e.uploadFileName,
                            e.uploadPath,
                            e.uploadFileName,
                            e.originFileName,
                            e.uploadStatus,
                            e.uploadStatus.description,
                            e.regName,
                            e.regTime,
                            e.updName,
                            e.updTime
                        )
                    }.toList(), list.totalElements
                )
            }
        }
    }

    @Schema(description = "파일 업로드 조회 DTO")
    data class FileUploadListData(
        @Schema(description = "파일 일련번호")
        var fileSeq: Long?,
        @Schema(description = "업로드 전체 경로")
        var uploadFullPath: String?,
        @Schema(description = "업로드 경로")
        var uploadPath: String?,
        @Schema(description = "업로드 파일 명")
        var uploadFileName: String?,
        @Schema(description = "원본 파일 명")
        var originFileName: String?,
        @Schema(description = "업로드 상태")
        var uploadStatus: FileUploadStatus?,
        @Schema(description = "업로드 상태 명")
        var uploadStatusName: String?,
        @Schema(description = "등록자")
        var regName: String?,
        @Schema(description = "등록시간")
        var regTime: LocalDateTime?,
        @Schema(description = "수정자")
        var updName: String?,
        @Schema(description = "수정시간")
        var updTime: LocalDateTime?,
    ) {
    }
}
