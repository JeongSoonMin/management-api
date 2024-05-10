package ai.fassto.management.global.exception

import ai.fassto.management.global.common.log
import ai.fassto.management.global.enums.LogType
import ai.fassto.management.global.enums.ResponseCode
import ai.fassto.management.global.model.CommonResponse
import lombok.extern.slf4j.Slf4j
import org.apache.coyote.BadRequestException
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {
    val log = this.log()

    @ExceptionHandler(
        BadRequestException::class,
        IllegalArgumentException::class,
        MissingServletRequestParameterException::class,
        HttpRequestMethodNotSupportedException::class,
        MissingRequestHeaderException::class,
        HttpMessageNotReadableException::class,
        TypeMismatchException::class
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(e: Exception): CommonResponse<Nothing> {
        log.warn("Bad Request Error : {}", e.message)
        return CommonResponse.fail(ResponseCode.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): CommonResponse<Nothing> {
        val message = e.bindingResult.allErrors.get(0).defaultMessage.toString()
        log.warn("Bad Request Valid Error : {}", message)
        return CommonResponse.fail(message, ResponseCode.BAD_REQUEST)
    }

    @ExceptionHandler(NoResourceFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNoResourceFoundException(e: Exception): CommonResponse<Nothing> {
        log.warn("Not Found Error : {}", e.message)
        return CommonResponse.fail(ResponseCode.NOT_FOUND)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleInternalServerException(e: Exception): CommonResponse<Nothing> {
        log.error("[UnknownException] {}", e.message, e)
        return CommonResponse.fail(ResponseCode.INTERNAL_SERVER_ERROR)
    }

    /**
     * 공통 server 예외 처리
     */
    @ExceptionHandler(BaseException::class)
    fun handleBusinessException(e: BaseException): ResponseEntity<CommonResponse<Any>> {
        var message: String = e.message.toString()
        if (message.isEmpty())
            message = e.responseCode.message

        if (LogType.WARN.equals(e.responseCode.logType))
            log.warn("[Exception] {}", message)
        else
            log.error("[Exception] {}", message, e)

        return ResponseEntity<CommonResponse<Any>>(
            CommonResponse.fail(message, e.responseCode, e.data),
            e.responseCode.status
        )
    }
}