package com.jesomi.management.application.controller

import com.jesomi.management.application.model.SampleDto
import com.jesomi.management.application.service.SampleService
import com.jesomi.management.global.configuration.SwaggerConfig
import com.jesomi.management.global.model.CommonResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@Tag(name = SwaggerConfig.SwaggerTags.SAMPLE)
@RestController
@RequestMapping("/sample")
class SampleController(
    val sampleService: SampleService
) {

    @Operation(summary = "샘플 목록 조회")
    @GetMapping("")
    fun sampleList(@Schema(description = "페이지 번호(0부터 시작)") @RequestParam("page", defaultValue = "0") page: Int,
                   @Schema(description = "페이지당 노출 수") @RequestParam("size", defaultValue = "20") size: Int): CommonResponse<SampleDto.SampleListResponse> {
        return CommonResponse.success(sampleService.findAll(page, size))
    }

    @Operation(summary = "샘플 등록")
    @PostMapping("")
    fun sampleCreate(@RequestBody @Valid sampleRequest: SampleDto.SampleCreateRequest): CommonResponse<Nothing> {
        sampleService.create(sampleRequest)
        return CommonResponse.success()
    }

    @Operation(summary = "샘플 상세 조회")
    @GetMapping("/{sampleId}")
    fun sample(@Schema(description = "샘플 ID") @PathVariable sampleId: Long): CommonResponse<SampleDto.SampleData> {
        return CommonResponse.success(
            sampleService.findById(sampleId)
            , "샘플 정보 조회 완료."
        )
    }

    @Operation(summary = "샘플 상세 조회 실패 테스트")
    @GetMapping("/{sampleId}/fail")
    fun sampleFail(@Schema(description = "샘플 ID") @PathVariable sampleId: Long): CommonResponse<Any> {
        return CommonResponse.success(sampleService.sampleException(sampleId))
    }

    @Operation(summary = "샘플 삭제")
    @DeleteMapping("/{sampleId}")
    fun sampleDelete(@Schema(description = "샘플 ID") @PathVariable sampleId: Long): CommonResponse<Nothing> {
        sampleService.delete(sampleId)
        return CommonResponse.success()
    }
}