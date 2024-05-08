package ai.fassto.management.service.facade

import ai.fassto.management.persistence.entity.Sample
import ai.fassto.management.persistence.repository.SampleRepository
import ai.fassto.management.service.SampleService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SampleFacade(
    private val sampleService: SampleService,
    private val sampleRepository: SampleRepository
) {

    @Transactional
    fun saveAndFindAll(sampleName: String, regName: String): List<Sample> {
        val sample = Sample.create(sampleName, regName)
        sampleService.save(sample)
        Thread.sleep(1000)

        return sampleService.findAll()
    }
}