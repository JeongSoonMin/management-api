package ai.fassto.management.global.enums

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val errorType: ErrorType,
    val message: String,
) {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, ErrorType.WARN, "잘못된 요청 입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, ErrorType.WARN, "잘못된 요청 입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.ERROR, "요청에 실패 하였습니다."),

    SAMPLE_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY, ErrorType.WARN, "Sample not found."),
}