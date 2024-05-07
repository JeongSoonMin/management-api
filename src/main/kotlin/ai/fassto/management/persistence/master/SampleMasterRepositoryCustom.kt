package ai.fassto.management.persistence.master

import ai.fassto.management.entity.Sample

interface SampleMasterRepositoryCustom {
    fun findBySampleName(sampleName: String): List<Sample>
}