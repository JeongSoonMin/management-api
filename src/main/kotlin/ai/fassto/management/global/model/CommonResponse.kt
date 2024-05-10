package ai.fassto.management.global.model

import ai.fassto.management.global.enums.ErrorCode
import ai.fassto.management.global.enums.ErrorType
import ai.fassto.management.global.enums.ResponseResult

data class CommonResponse<T>(
    val result: ResponseResult,
    val data: T?,
    val message: String?,
    val errorCode: ErrorCode?,
    val errorType: ErrorType?
) {
    companion object {
        fun success(): CommonResponse<Nothing> {
            return CommonResponse(ResponseResult.SUCCESS, null, null, null, null)
        }

        fun <T> success(data: T?): CommonResponse<T> {
            return CommonResponse(ResponseResult.SUCCESS, data, null, null, null)
        }

        fun <T> success(data: T?, message: String): CommonResponse<T> {
            return CommonResponse(ResponseResult.SUCCESS, data, message, null, null)
        }

        fun fail(message: String, errorCode: ErrorCode): CommonResponse<Nothing> {
            return CommonResponse(
                ResponseResult.FAIL,
                null,
                message,
                errorCode,
                errorCode.errorType
            )
        }

        fun fail(errorCode: ErrorCode): CommonResponse<Nothing> {
            return CommonResponse(
                ResponseResult.FAIL,
                null,
                errorCode.message,
                errorCode,
                errorCode.errorType
            )
        }

        fun <T> fail(errorCode: ErrorCode, data: T?): CommonResponse<T> {
            return CommonResponse(
                ResponseResult.FAIL,
                data,
                errorCode.message,
                errorCode,
                errorCode.errorType
            )
        }

        fun <T> fail(message: String, errorCode: ErrorCode, data: T?): CommonResponse<T> {
            return CommonResponse(
                ResponseResult.FAIL,
                data,
                message,
                errorCode,
                errorCode.errorType
            )
        }

    }
}
