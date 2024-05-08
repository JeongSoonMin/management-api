package ai.fassto.management.presentation.controller

import ai.fassto.management.global.enums.ErrorCode
import ai.fassto.management.global.exception.BaseException
import ai.fassto.management.global.response.CommonResponse
import ai.fassto.management.service.SampleService
import ai.fassto.management.service.facade.SampleFacade
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sample")
class SampleController(
    val sampleFacade: SampleFacade,
    val sampleService: SampleService
) {

    @GetMapping("")
    fun sampleList(): CommonResponse<Any> {
        return CommonResponse.success(sampleService.findAll())
    }

    @GetMapping("/{sampleId}")
    fun sample(@PathVariable sampleId: Long): CommonResponse<Any> {
        return CommonResponse.success(
            sampleService.findById(sampleId)
                .orElseThrow { throw BaseException(ErrorCode.SAMPLE_NOT_FOUND) }
        )
    }

    @GetMapping("/{sampleId}/fail")
    fun sampleFail(@PathVariable sampleId: Long): CommonResponse<Any> {
        return CommonResponse.success(sampleService.sampleException(sampleId))
    }

    @DeleteMapping("/{sampleId}")
    fun sampleDelete(@PathVariable sampleId: Long): CommonResponse<Nothing> {
        sampleService.delete(sampleId)
        return CommonResponse.success()
    }
}