package ai.fassto.management.persistence.repository.impl

import ai.fassto.management.persistence.entity.QSample.sample
import ai.fassto.management.persistence.entity.Sample
import ai.fassto.management.persistence.repository.SampleRepositoryCustom
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