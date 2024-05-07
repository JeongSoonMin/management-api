package ai.fassto.management.persistence.master.impl

import ai.fassto.management.entity.QSample
import ai.fassto.management.entity.Sample
import ai.fassto.management.persistence.master.SampleMasterRepositoryCustom
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Qualifier

class SampleMasterRepositoryCustomImpl(
    @Qualifier("masterJpaQueryFactory") private val masterJPAQueryFactory: JPAQueryFactory
): SampleMasterRepositoryCustom {
    override fun findBySampleName(sampleName: String): List<Sample> {
        val sample = QSample.sample;
        return masterJPAQueryFactory.select(sample)
            .from(sample)
            .where(
                sample.sampleName.contains(sampleName)
            ).fetch();
    }
}