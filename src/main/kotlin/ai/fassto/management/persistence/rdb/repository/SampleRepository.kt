package ai.fassto.management.persistence.rdb.repository

import ai.fassto.management.persistence.rdb.entity.Sample
import org.springframework.data.jpa.repository.JpaRepository

interface SampleRepository: JpaRepository<Sample, Long>, SampleRepositoryCustom