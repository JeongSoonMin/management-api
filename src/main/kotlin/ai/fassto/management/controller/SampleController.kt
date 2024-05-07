package ai.fassto.management.controller

import ai.fassto.management.entity.Sample
import ai.fassto.management.facade.SampleFacade
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sample")
class SampleController(val sampleFacade: SampleFacade) {
    @GetMapping("")
    fun sampleList(): List<Sample> {
        return sampleFacade.saveAndFindAll()
    }
}