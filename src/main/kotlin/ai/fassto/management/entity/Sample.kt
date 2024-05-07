package ai.fassto.management.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "sample")
class Sample(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sample_id")
    var sampleId: Long? = null,

    @Column(name = "sample_name")
    var sampleName: String? = null,

    @Column(name = "reg_time")
    var regTime: LocalDateTime? = null,

    @Column(name = "upd_time")
    var updTime: LocalDateTime? = null
)
