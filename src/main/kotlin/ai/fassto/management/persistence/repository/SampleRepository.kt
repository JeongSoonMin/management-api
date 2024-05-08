package ai.fassto.management.persistence.repository

import ai.fassto.management.persistence.entity.Sample
import org.springframework.data.jpa.repository.JpaRepository

interface SampleRepository: JpaRepository<Sample, Long>, SampleRepositoryCustom