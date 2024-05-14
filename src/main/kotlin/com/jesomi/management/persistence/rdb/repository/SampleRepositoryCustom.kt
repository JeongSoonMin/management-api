package com.jesomi.management.persistence.rdb.repository

import com.jesomi.management.persistence.rdb.entity.Sample

interface SampleRepositoryCustom {
    fun findBySampleName(sampleName: String): List<Sample>
}