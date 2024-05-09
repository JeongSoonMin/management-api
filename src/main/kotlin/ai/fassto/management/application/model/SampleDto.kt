package ai.fassto.management.application.model

import ai.fassto.management.persistence.entity.Sample
import org.springframework.data.domain.Page
import java.time.LocalDateTime

class SampleDto {
    data class SampleCreateRequest(
        val sampleId: Long
    ) {}

    data class SampleListResponse(
        var content: List<SampleData>,
        var total: Long
    ) {
        companion object {
            fun of(sampleList: Page<SampleData>): SampleListResponse {
                return SampleListResponse(sampleList.content, sampleList.totalElements)
            }
        }
    }

    data class SampleResponse(
        val sampleName: String,
        val regDate: LocalDateTime,
        val updDate: LocalDateTime
    )

    data class SampleData(
        var sampleId: Long?,
        var sampleName: String,
        var regName: String?
    ) {
        companion object {
            fun of(sample: Sample): SampleData {
                return SampleData(sample.sampleId, sample.sampleName, sample.regName)
            }

            fun toEntity(sampleData: SampleData): Sample {
                return Sample.create(sampleData.sampleName, sampleData.regName)
            }
        }
    }
}
