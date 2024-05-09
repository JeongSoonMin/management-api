package ai.fassto.management.application.service

import ai.fassto.management.application.model.SampleDto
import ai.fassto.management.persistence.entity.Sample

interface SampleService {
    fun findAll(page: Int, size: Int): SampleDto.SampleListResponse

    fun findById(sampleId: Long): SampleDto.SampleData?

    fun save(sample: Sample)

    fun delete(sampleId: Long)

    fun sampleException(sampleId: Long): Any

    fun saveAndFindAll(sampleName: String, regName: String): SampleDto.SampleListResponse
}