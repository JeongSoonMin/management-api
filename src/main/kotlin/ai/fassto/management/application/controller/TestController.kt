package ai.fassto.management.application.controller

import ai.fassto.management.application.service.TestService
import ai.fassto.management.global.configuration.SwaggerConfig
import ai.fassto.management.global.model.CommonResponse
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
        testService.virtualThreadTest()
        return CommonResponse.success()
    }
}