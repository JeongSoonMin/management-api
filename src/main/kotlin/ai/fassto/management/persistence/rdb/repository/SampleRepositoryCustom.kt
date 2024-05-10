package ai.fassto.management.persistence.rdb.repository

import ai.fassto.management.persistence.rdb.entity.Sample

interface SampleRepositoryCustom {
    fun findBySampleName(sampleName: String): List<Sample>
}