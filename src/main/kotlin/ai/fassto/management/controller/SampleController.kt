package ai.fassto.management.controller

import ai.fassto.management.entity.Sample
import ai.fassto.management.facade.SampleFacade
import ai.fassto.management.service.SampleService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sample")
class SampleController(
    val sampleFacade: SampleFacade,
    val sampleService: SampleService
) {

    @GetMapping("")
    fun sampleList(): List<Sample> {
        return sampleFacade.saveAndFindAll()
    }

    @GetMapping("/{sampleId}")
    fun sample(@PathVariable sampleId: Long): Sample {
        val sample = sampleService.findById(sampleId)
        if(sample.isPresent) {
            return sample.get()
        }
        return Sample()
    }

    @DeleteMapping("/{sampleId}")
    fun sampleDelete(@PathVariable sampleId: Long) {
        sampleService.delete(sampleId)
    }
}