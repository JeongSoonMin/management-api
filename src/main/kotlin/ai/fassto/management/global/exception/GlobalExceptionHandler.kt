package ai.fassto.management.global.exception

import ai.fassto.management.global.enums.ErrorCode
import ai.fassto.management.global.enums.ErrorType
import ai.fassto.management.global.response.CommonResponse
import lombok.extern.slf4j.Slf4j
import org.apache.coyote.BadRequestException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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
    val logger: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(
        BadRequestException::class,
        IllegalArgumentException::class,
        MissingServletRequestParameterException::class,
        HttpRequestMethodNotSupportedException::class,
        MissingRequestHeaderException::class,
        HttpMessageNotReadableException::class,
        TypeMismatchException::class,
        MethodArgumentNotValidException::class
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(e: Exception): CommonResponse<Nothing> {
        logger.warn("Bad Request Error : {}", e.message)
        return CommonResponse.fail(ErrorCode.BAD_REQUEST)
    }

    @ExceptionHandler(NoResourceFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNoResourceFoundException(e: Exception): CommonResponse<Nothing> {
        logger.warn("Not Found Error : {}", e.message)
        return CommonResponse.fail(ErrorCode.NOT_FOUND)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleInternalServerException(e: Exception): CommonResponse<Nothing> {
        logger.error("[UnknownException] {}", e.message, e)
        return CommonResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR)
    }

    /**
     * 공통 server 예외 처리
     */
    @ExceptionHandler(BaseException::class)
    fun handleBusinessException(e: BaseException): ResponseEntity<CommonResponse<Any>> {
        if (ErrorType.WARN.equals(e.errorCode.errorType))
            logger.warn("[Exception] {}", e.message, e)
        else
            logger.error("[Exception] {}", e.message, e)

        return ResponseEntity<CommonResponse<Any>>(
            CommonResponse.fail(e.errorCode, e.data),
            e.errorCode.status
        )
    }
}