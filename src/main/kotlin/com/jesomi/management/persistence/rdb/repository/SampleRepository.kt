package com.jesomi.management.persistence.rdb.repository

import com.jesomi.management.persistence.rdb.entity.Sample
import org.springframework.data.jpa.repository.JpaRepository

interface SampleRepository: JpaRepository<Sample, Long>, SampleRepositoryCustom