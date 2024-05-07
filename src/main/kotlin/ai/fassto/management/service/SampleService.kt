package ai.fassto.management.service

import ai.fassto.management.entity.Sample
import ai.fassto.management.repository.SampleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SampleService(
    private val sampleRepository: SampleRepository
) {

    @Transactional(readOnly = true)
    fun findAll(): List<Sample> {
        return sampleRepository.findAll().toList()
    }


    @Transactional
    fun save(sample: Sample): Sample {
        return sampleRepository.save(sample)
    }
}