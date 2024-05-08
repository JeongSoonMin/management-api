package ai.fassto.management.service

import ai.fassto.management.persistence.entity.Sample
import ai.fassto.management.persistence.repository.SampleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class SampleService(
    private val sampleRepository: SampleRepository
) {

    fun findAll(): List<Sample> {
        return sampleRepository.findAll().toList()
    }

    fun findById(sampleId: Long): Optional<Sample> {
        return sampleRepository.findById(sampleId)
    }

    @Transactional
    fun save(sample: Sample): Sample {
        return sampleRepository.save(sample)
    }

    @Transactional
    fun delete(sampleId: Long) {
        findById(sampleId).ifPresent(sampleRepository::delete)
    }
}