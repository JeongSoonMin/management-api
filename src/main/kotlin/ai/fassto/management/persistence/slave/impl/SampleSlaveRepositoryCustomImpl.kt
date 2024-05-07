package ai.fassto.management.persistence.slave.impl

import ai.fassto.management.entity.QSample
import ai.fassto.management.entity.Sample
import ai.fassto.management.persistence.slave.SampleSlaveRepositoryCustom
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Qualifier

class SampleSlaveRepositoryCustomImpl(
    @Qualifier("slaveJpaQueryFactory") private val slaveJPAQueryFactory: JPAQueryFactory
): SampleSlaveRepositoryCustom {
    override fun findBySampleName(sampleName: String): List<Sample> {
        val sample = QSample.sample;
        return slaveJPAQueryFactory.select(sample)
            .from(sample)
            .where(
                sample.sampleName.contains(sampleName)
            ).fetch();
    }
}