package ai.fassto.management.controller

import ai.fassto.management.entity.Sample
import ai.fassto.management.service.SampleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sample")
class SampleController(val sampleService: SampleService) {
    @GetMapping("")
    fun sampleList(): List<Sample> {
        return sampleService.saveAndFindAll();
    }
}