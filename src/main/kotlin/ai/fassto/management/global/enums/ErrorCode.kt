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

    FILE_MANAGEMENT_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.ERROR, "파일 관리 요청에 실패하였습니다."),
    NOT_SUPPORTED_UPLOAD_TYPE(HttpStatus.UNPROCESSABLE_ENTITY, ErrorType.WARN, "지원되지 않는 파일 업로드 유형입니다.")
}