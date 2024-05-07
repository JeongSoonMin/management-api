package ai.fassto.management.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
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
    @CreationTimestamp
    var regTime: LocalDateTime? = null,

    @Column(name = "reg_name")
    var regName: String? = null,

    @Column(name = "upd_time")
    @UpdateTimestamp
    var updTime: LocalDateTime? = null,

    @Column(name = "upd_name")
    var updName: String? = null
)
