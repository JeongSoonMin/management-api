package com.jesomi.management.persistence.rdb.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseDateEntity {
    @Column(name = "reg_time", updatable = false)
    @CreatedDate
    var regTime: LocalDateTime? = null
        protected set

    @Column(name = "upd_time")
    @LastModifiedDate
    var updTime: LocalDateTime? = null
        protected set
}