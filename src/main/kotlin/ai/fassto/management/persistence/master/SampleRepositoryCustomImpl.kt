package ai.fassto.management.persistence.master

import ai.fassto.management.entity.QSample
import ai.fassto.management.entity.Sample
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Qualifier

class SampleRepositoryCustomImpl(
    @Qualifier("masterJpaQueryFactory") private val masterJPAQueryFactory: JPAQueryFactory
): SampleRepositoryCustom {
    override fun findBySampleName(sampleName: String): List<Sample> {
        var sample = QSample.sample;
        return masterJPAQueryFactory.select(sample)
            .from(sample)
            .where(
                sample.sampleName.eq(sampleName)
            ).fetch();
    }
}