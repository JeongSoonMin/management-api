package ai.fassto.management.controller

import ai.fassto.management.entity.Sample
import ai.fassto.management.persistence.master.SampleMasterRepository
import ai.fassto.management.persistence.slave.SampleSlaveRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/sample")
class SampleController(val sampleSlaveRepository: SampleSlaveRepository,
    val sampleMasterRepository: SampleMasterRepository) {
    @GetMapping("")
    fun sampleList(): List<Sample> {
        val sample = Sample(1234, "asdf", LocalDateTime.now(), LocalDateTime.now());
        sampleMasterRepository.save(sample);

        return sampleSlaveRepository.findAll().toList();
    }
}