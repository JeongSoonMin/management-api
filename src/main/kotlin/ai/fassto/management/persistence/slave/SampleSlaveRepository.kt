package ai.fassto.management.persistence.slave

import ai.fassto.management.entity.Sample
import org.springframework.data.jpa.repository.JpaRepository

interface SampleSlaveRepository: JpaRepository<Sample, String>