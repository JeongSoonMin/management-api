package ai.fassto.management.service

import ai.fassto.management.global.enums.ErrorCode
import ai.fassto.management.global.exception.BaseException
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

    fun sampleException(sampleId: Long): Any {
//        throw BaseException()
//        throw BaseException("에러가 발생하였습니다.")
//        throw BaseException("에러 발생", this.findAll())
        throw BaseException(ErrorCode.BAD_REQUEST)
//        throw BaseException(ErrorCode.INTERNAL_SERVER_ERROR, this.findAll())
    }
}