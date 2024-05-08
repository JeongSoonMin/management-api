package ai.fassto.management.persistence.repository

import ai.fassto.management.persistence.entity.Sample

interface SampleRepositoryCustom {
    fun findBySampleName(sampleName: String): List<Sample>
}