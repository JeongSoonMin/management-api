package ai.fassto.management.persistence.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "sample")
class Sample(
    @Column(name = "sample_name")
    var sampleName: String,

    @Column(name = "reg_time")
    @CreatedDate
    var regTime: LocalDateTime? = null,

    @Column(name = "reg_name")
    @CreatedBy
    var regName: String,

    @Column(name = "upd_time")
    @LastModifiedDate
    var updTime: LocalDateTime? = null,

    @Column(name = "upd_name")
    @LastModifiedBy
    var updName: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sample_id")
    var sampleId: Long? = null

    companion object {
        fun create(sampleName: String, regName: String): Sample {
            return Sample(sampleName, LocalDateTime.now(), regName, LocalDateTime.now(), regName)
        }
    }
}
