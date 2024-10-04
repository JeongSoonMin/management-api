package com.jesomi.management.application.controller

import com.jesomi.management.application.service.TestService
import com.jesomi.management.global.configuration.SwaggerConfig
import com.jesomi.management.global.model.CommonResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = SwaggerConfig.SwaggerTags.TEST)
@RestController
@RequestMapping("/test")
class TestController(
    val testService: TestService
) {

    @Operation(summary = "가상 Thread Test")
    @GetMapping("/virtual-thread-test")
    fun virtualThreadTest(): CommonResponse<Nothing> {
        testService.virtualThreadAsyncTest()
        return CommonResponse.success()
    }

    @Operation(summary = "가상 Thread Test2")
    @GetMapping("/virtual-thread-test-2")
    fun virtualThreadTest2(): CommonResponse<Nothing> {
        testService.virtualThreadTest()
        return CommonResponse.success()
    }

    @Operation(summary = "cacheable 테스트")
    @GetMapping("/cacheable")
    fun cacheable(): CommonResponse<Any> {
        return CommonResponse.success(testService.findSampleListCache())
    }

    @Operation(summary = "kafka produce 테스트")
    @GetMapping("/kafka-produce")
    fun kafkaProduce(): CommonResponse<Any> {
        return CommonResponse.success(testService.kafkaProduce())
    }
}