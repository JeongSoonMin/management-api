package ai.fassto.management.facade

import ai.fassto.management.entity.Sample
import ai.fassto.management.service.SampleService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class SampleFacade(private val sampleService: SampleService) {

    @Transactional
    fun saveAndFindAll(): List<Sample> {
        val sample = Sample(null, "asdf1", LocalDateTime.now(), LocalDateTime.now());
        sampleService.save(sample);
        Thread.sleep(1000);

        return sampleService.findAll();
    }
}