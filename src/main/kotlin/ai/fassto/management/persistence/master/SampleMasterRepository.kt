package ai.fassto.management.persistence.master

import ai.fassto.management.entity.Sample
import org.springframework.data.jpa.repository.JpaRepository

interface SampleMasterRepository: JpaRepository<Sample, String>, SampleMasterRepositoryCustom {
}