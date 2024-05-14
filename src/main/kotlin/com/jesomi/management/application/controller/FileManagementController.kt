package com.jesomi.management.application.controller

import com.jesomi.management.application.service.FileManagementService
import com.jesomi.management.global.configuration.SwaggerConfig
import com.jesomi.management.global.model.CommonResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = SwaggerConfig.SwaggerTags.COMMON)
@RestController
@RequestMapping("/files")
class FileManagementController(
    val fileManagementService: FileManagementService
) {

    @Operation(summary = "S3 파일 업로드 URL 생성 요청")
    @GetMapping("pre-signed-url")
    fun preSignedUrl(@Schema(description = "업로드 파일 명") @RequestParam fileName: String,
                     @Schema(description = "업로드 유형(event or xxx)") @RequestParam uploadType: String): CommonResponse<String> {
        return CommonResponse.success(fileManagementService.generatePreSignedUrl(uploadType, fileName))
    }

}