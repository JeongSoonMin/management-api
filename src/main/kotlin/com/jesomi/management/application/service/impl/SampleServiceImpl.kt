package com.jesomi.management.application.service.impl

import com.jesomi.management.application.model.SampleDto
import com.jesomi.management.application.service.SampleService
import com.jesomi.management.global.common.log
import com.jesomi.management.global.enums.ResponseCode
import com.jesomi.management.global.exception.BaseException
import com.jesomi.management.persistence.rdb.entity.Sample
import com.jesomi.management.persistence.rdb.repository.SampleRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true) // transactional 명시되지 않은 메소드는 slave 조회하도록 readOnly 설정
class SampleServiceImpl(
    private val sampleRepository: SampleRepository
): SampleService {
    val log = this.log()

    override fun findAll(page: Int, size: Int): SampleDto.SampleListResponse {
        log.info("Sample List 조회");
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
        log.info("create >>> {}", sampleCreateRequest)
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