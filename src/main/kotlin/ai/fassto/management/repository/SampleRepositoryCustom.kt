package ai.fassto.management.repository

import ai.fassto.management.entity.Sample

interface SampleRepositoryCustom {
    fun findBySampleName(sampleName: String): List<Sample>
}