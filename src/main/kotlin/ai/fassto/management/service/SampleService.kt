package ai.fassto.management.service

import ai.fassto.management.entity.Sample
import ai.fassto.management.repository.SampleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.jvm.optionals.getOrNull

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