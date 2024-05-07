package ai.fassto.management.repository

import ai.fassto.management.entity.Sample
import org.springframework.data.jpa.repository.JpaRepository

interface SampleRepository: JpaRepository<Sample, String>, SampleRepositoryCustom {
}