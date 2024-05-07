package ai.fassto.management.persistence.slave

import ai.fassto.management.entity.Sample

interface SampleSlaveRepositoryCustom {
    fun findBySampleName(sampleName: String): List<Sample>
}