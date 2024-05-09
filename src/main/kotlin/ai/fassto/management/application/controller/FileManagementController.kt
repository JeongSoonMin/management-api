package ai.fassto.management.application.controller

import ai.fassto.management.application.service.FileManagementService
import ai.fassto.management.global.model.CommonResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/files")
class FileManagementController(
    val fileManagementService: FileManagementService
) {

    @GetMapping("pre-signed-url")
    fun preSignedUrl(@RequestParam fileName: String, @RequestParam uploadType: String): CommonResponse<Any> {
        return CommonResponse.success(fileManagementService.generatePreSignedUrl(uploadType, fileName))
    }

}