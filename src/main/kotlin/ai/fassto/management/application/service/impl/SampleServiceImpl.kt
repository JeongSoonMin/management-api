package ai.fassto.management.application.service.impl

import ai.fassto.management.application.model.SampleDto
import ai.fassto.management.application.service.SampleService
import ai.fassto.management.global.enums.ResponseCode
import ai.fassto.management.global.exception.BaseException
import ai.fassto.management.persistence.entity.Sample
import ai.fassto.management.persistence.repository.SampleRepository
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
@Transactional(readOnly = true) // transactional 명시되지 않은 메소드는 slave 조회하도록 readOnly 설정
class SampleServiceImpl(
    private val sampleRepository: SampleRepository
): SampleService {
    val logger: Logger = LoggerFactory.getLogger(SampleService::class.java)

    override fun findAll(page: Int, size: Int): SampleDto.SampleListResponse {
        logger.info("Sample List 조회");
        val pageable = PageRequest.of(page, size, Sort.by("sampleId").descending())
        return SampleDto.SampleListResponse.of(sampleRepository.findAll(pageable).map { e -> SampleDto.SampleData.of(e) })
    }

    override fun findById(sampleId: Long): SampleDto.SampleData? {
        return sampleRepository.findById(sampleId)
            .map { e -> SampleDto.SampleData.of(e) }
            .orElseThrow { throw BaseException(ResponseCode.SAMPLE_NOT_FOUND) }
    }

    @Transactional
    override fun create(sampleCreateRequest: SampleDto.SampleCreateRequest) {
        logger.info("create >>> {}", sampleCreateRequest)
        sampleRepository.save(sampleCreateRequest.toEntity())
    }

    @Transactional
    override // readOnly 옵션이 없을 경우 master에서 등록/수정 및 조회
    fun save(sample: Sample) {
        sampleRepository.save(sample)
    }

    @Transactional
    override fun delete(sampleId: Long) {
        sampleRepository.findById(sampleId).ifPresent(sampleRepository::delete)
    }

    override fun sampleException(sampleId: Long): Any {
//        throw BaseException()
        throw BaseException("에러가 발생하였습니다.")
//        throw BaseException("에러 발생", this.findAll())
//        throw BaseException(ErrorCode.BAD_REQUEST)
//        throw BaseException(ErrorCode.INTERNAL_SERVER_ERROR, this.findAll())
    }

    @Transactional
    override fun saveAndFindAll(sampleName: String, regName: String): SampleDto.SampleListResponse {
        val sample = Sample.create(sampleName, regName)
        save(sample)
        val sampleList = findAll(0, 20)
        val sampleData = sampleList.content[0]
        sampleData.sampleName = "testSample"
        sampleList.content.stream()
            .map { e -> SampleDto.SampleData.toEntity(e) }

        return sampleList
    }
}