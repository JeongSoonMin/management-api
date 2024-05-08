package ai.fassto.management.service

import ai.fassto.management.persistence.entity.Sample
import ai.fassto.management.persistence.repository.SampleRepository
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Slf4j
@Service
@Transactional(readOnly = true)
class SampleService(
    private val sampleRepository: SampleRepository
) {
    val logger: Logger = LoggerFactory.getLogger(SampleService::class.java)

    fun findAll(): List<Sample> {
        logger.info("Sample List 조회");

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