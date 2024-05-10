package ai.fassto.management.persistence.rdb.repository.impl

import ai.fassto.management.persistence.rdb.entity.QSample.sample
import ai.fassto.management.persistence.rdb.entity.Sample
import ai.fassto.management.persistence.rdb.repository.SampleRepositoryCustom
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