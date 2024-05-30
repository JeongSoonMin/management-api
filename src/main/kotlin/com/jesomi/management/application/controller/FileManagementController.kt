package com.jesomi.management.application.controller

import com.jesomi.management.application.model.FileDto
import com.jesomi.management.application.service.FileManagementService
import com.jesomi.management.global.configuration.SwaggerConfig
import com.jesomi.management.global.model.CommonResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@Tag(name = SwaggerConfig.SwaggerTags.COMMON)
@RestController
@RequestMapping("/files")
class FileManagementController(
    val fileManagementService: FileManagementService
) {

    @Operation(summary = "파일 업로드 URL 생성 요청")
    @GetMapping("/pre-signed-url/{fileManageCd}")
    fun preSignedUrl(
        @Schema(description = "파일 관리 코드(EVENT or xxx)") @PathVariable fileManageCd: String,
        @Schema(description = "업로드 파일 명") @RequestParam fileName: String,
        @Schema(description = "요청자 ID") @RequestParam reqId: String
    ): CommonResponse<FileDto.FilePreSignedResponse> {
        return CommonResponse.success(
            fileManagementService.generatePreSignedUrl(
                fileManageCd,
                fileName,
                reqId
            )
        )
    }

    @Operation(summary = "파일 업로드 목록 조회")
    @GetMapping("/upload/{fileManageCd}")
    fun fileUploadList(
        @Schema(description = "파일 관리 코드") @PathVariable fileManageCd: String,
        @Schema(description = "페이지 번호(1부터 시작)") @RequestParam("page", defaultValue = "1") page: Int,
        @Schema(description = "페이지당 노출 수") @RequestParam("size", defaultValue = "20") size: Int
    ): CommonResponse<FileDto.FileUploadListResponse> {
        return CommonResponse.success(
            fileManagementService.findAllFileUploadByFileManageCd(fileManageCd, page, size)
        )
    }

    @Operation(summary = "파일 업로드 완료")
    @PostMapping("/upload/{fileManageCd}")
    fun completeFileUpload(
        @Schema(description = "파일 관리 코드") @PathVariable fileManageCd: String,
        @RequestBody @Valid request: FileDto.FileUploadCompleteRequest
    ): CommonResponse<FileDto.FileUploadCompleteResponse> {
        val uploadId: String = request.uploadId.toString()
        val reqId: String = request.reqId.toString()
        return CommonResponse.success(
            fileManagementService.completeFileUpload(uploadId, reqId)
        )
    }

    @Operation(summary = "파일 업로드 삭제")
    @DeleteMapping("/upload/{fileManageCd}/{fileSeq}")
    fun deleteFileUpload(
        @Schema(description = "파일 관리 코드") @PathVariable fileManageCd: String,
        @Schema(description = "파일 일련번호") @PathVariable fileSeq: Long,
        @Schema(description = "요청자 ID") @RequestParam reqId: String
    ): CommonResponse<Nothing> {
        fileManagementService.deleteFileUpload(fileSeq, reqId)
        return CommonResponse.success()
    }

}