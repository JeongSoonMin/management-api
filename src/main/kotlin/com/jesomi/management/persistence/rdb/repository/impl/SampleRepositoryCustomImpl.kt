package com.jesomi.management.persistence.rdb.repository.impl

import com.jesomi.management.persistence.rdb.entity.QSample.sample
import com.jesomi.management.persistence.rdb.entity.Sample
import com.jesomi.management.persistence.rdb.repository.SampleRepositoryCustom
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class SampleRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
): SampleRepositoryCustom {
    override fun findBySampleName(sampleName: String): List<Sample> {
        return queryFactory.select(sample)
            .from(sample)
            .where(
                sample.sampleName.contains(sampleName)
            ).fetch()
    }
}