package ai.fassto.management.application.model

import ai.fassto.management.global.exception.BaseException
import ai.fassto.management.persistence.rdb.entity.Sample
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import org.springframework.data.domain.Page
import java.time.LocalDateTime

class SampleDto {

    @Schema(description = "샘플 등록 DTO")
    data class SampleCreateRequest(
        @Schema(description = "샘플 명")
        @field:NotNull(message = "샘플명은 필수로 입력해주시기 바랍니다.")
        val sampleName: String?,
        @Schema(description = "샘플 설명")
        @field:NotNull(message = "샘플 설명은 필수로 입력해주시기 바랍니다.")
        val sampleDescription: String?,
        val regName: String
    ) {
        fun toEntity(): Sample {
            if (this.sampleName == null) throw BaseException("샘플 명이 입력되지 않았습니다.")
            return Sample.create(this.sampleName, this.regName)
        }
    }

    @Schema(description = "샘플 목록 조회 응답 DTO")
    data class SampleListResponse(
        @Schema(description = "샘플 목록")
        var content: List<SampleData>,
        @Schema(description = "샘플 전체 수")
        var total: Long
    ) {
        companion object {
            fun of(sampleList: Page<SampleData>): SampleListResponse {
                return SampleListResponse(sampleList.content, sampleList.totalElements)
            }
        }
    }

    @Schema(description = "샘플 조회 응답 DTO")
    data class SampleResponse(
        @Schema(description = "샘플 명")
        val sampleName: String,
        @Schema(description = "등록 시간")
        val regDate: LocalDateTime,
        @Schema(description = "수정 시간")
        val updDate: LocalDateTime
    )

    @Schema(description = "샘플 조회 DTO")
    data class SampleData(
        @Schema(description = "샘플 ID")
        var sampleId: Long?,
        @Schema(description = "샘플 명")
        var sampleName: String,
        @Schema(description = "등록자")
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
