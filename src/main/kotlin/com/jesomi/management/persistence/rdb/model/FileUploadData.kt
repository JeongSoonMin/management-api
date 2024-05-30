package com.jesomi.management.persistence.rdb.model

import com.jesomi.management.persistence.rdb.enums.FileUploadStatus
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime


@Schema(description = "파일 업로드 조회 Data")
class FileUploadData(
    @Schema(description = "파일 일련번호")
    val fileSeq: Long,
    @Schema(description = "도메인 명")
    val domainName: String,
    @Schema(description = "업로드 경로")
    val uploadPath: String,
    @Schema(description = "업로드 파일 명")
    val uploadFileName: String,
    @Schema(description = "원본 파일 명")
    val originFileName: String,
    @Schema(description = "업로드 상태")
    val uploadStatus: FileUploadStatus,
    @Schema(description = "등록자")
    val regName: String,
    @Schema(description = "등록시간")
    val regTime: LocalDateTime,
    @Schema(description = "수정자")
    val updName: String,
    @Schema(description = "수정시간")
    val updTime: LocalDateTime,
)