package ai.fassto.management.application.controller

import ai.fassto.management.application.service.SampleService
import ai.fassto.management.global.model.CommonResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sample")
class SampleController(
    val sampleService: SampleService
) {

    @GetMapping("")
    fun sampleList(@RequestParam("page", defaultValue = "0") page: Int, @RequestParam("size", defaultValue = "20") size: Int): CommonResponse<Any> {
        return CommonResponse.success(sampleService.findAll(page, size))
    }

    @GetMapping("/{sampleId}")
    fun sample(@PathVariable sampleId: Long): CommonResponse<Any> {
        return CommonResponse.success(
            sampleService.findById(sampleId)
            , "샘플 정보 조회 완료."
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