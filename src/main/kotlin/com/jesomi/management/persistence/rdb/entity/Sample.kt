package com.jesomi.management.persistence.rdb.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "sample")
class Sample(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sample_id")
    var sampleId: Long? = null,

    @Column(name = "sample_name")
    var sampleName: String,

    @Column(name = "reg_time")
    @CreatedDate
    var regTime: LocalDateTime? = null,

    @Column(name = "reg_name")
    @CreatedBy
    var regName: String?,

    @Column(name = "upd_time")
    @LastModifiedDate
    var updTime: LocalDateTime? = null,

    @Column(name = "upd_name")
    @LastModifiedBy
    var updName: String?
) {
    companion object {
        fun create(sampleName: String, regName: String?): Sample {
            return Sample(null, sampleName, LocalDateTime.now(), regName, LocalDateTime.now(), regName)
        }
    }
}
