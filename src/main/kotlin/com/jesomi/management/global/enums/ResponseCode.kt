package com.jesomi.management.global.enums

import org.springframework.http.HttpStatus

enum class ResponseCode(
    val status: HttpStatus,
    val logType: LogType,
    val message: String,
) {
    SUCCESS(HttpStatus.OK, LogType.INFO, "요청에 성공 하였습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, LogType.WARN, "잘못된 요청 입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, LogType.WARN, "잘못된 요청 입니다."),
    UNPROCESSABLE_ENTITY(HttpStatus.UNPROCESSABLE_ENTITY, LogType.ERROR, "요청에 실패 하였습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, LogType.ERROR, "오류가 발생 하였습니다. 관리자에 문의 바랍니다."),

    SAMPLE_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY, LogType.WARN, "Sample not found."),

    FILE_MANAGEMENT_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, LogType.ERROR, "파일 관리 요청에 실패하였습니다."),
    FILE_UPLOAD_PREPARED_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY, LogType.WARN, "파일 업로드 정보가 없습니다."),
    FILE_UPLOAD_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY, LogType.WARN, "파일 업로드 정보가 없습니다."),
    FILE_MANAGEMENT_FILE_EXISTS(HttpStatus.UNPROCESSABLE_ENTITY, LogType.WARN, "이미 동일한 파일이 존재 합니다."),
    FILE_UPLOAD_DELETED(HttpStatus.UNPROCESSABLE_ENTITY, LogType.WARN, "이미 삭제 된 파일입니다."),
    NOT_SUPPORTED_UPLOAD_TYPE(HttpStatus.UNPROCESSABLE_ENTITY, LogType.WARN, "지원 되지 않는 파일 업로드 유형입니다.")
}