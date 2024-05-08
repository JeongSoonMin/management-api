package ai.fassto.management.presentation.controller

import ai.fassto.management.persistence.entity.Sample
import ai.fassto.management.service.facade.SampleFacade
import ai.fassto.management.service.SampleService
import org.springframework.web.bind.annotation.*
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/sample")
class SampleController(
    val sampleFacade: SampleFacade,
    val sampleService: SampleService
) {

    @GetMapping("")
    fun sampleList(): List<Sample> {
        return sampleService.findAll()
    }

    @GetMapping("/{sampleId}")
    fun sample(@PathVariable sampleId: Long): Sample? {
        val sample = sampleService.findById(sampleId)
        return sample.getOrNull()
    }

    @DeleteMapping("/{sampleId}")
    fun sampleDelete(@PathVariable sampleId: Long) {
        sampleService.delete(sampleId)
    }
}