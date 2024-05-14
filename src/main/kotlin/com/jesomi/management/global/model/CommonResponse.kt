package com.jesomi.management.global.model

import com.jesomi.management.global.enums.ResponseCode
import com.jesomi.management.global.enums.LogType
import com.jesomi.management.global.enums.ResponseResult

data class CommonResponse<T>(
    val result: ResponseResult,
    val data: T?,
    val message: String?,
    val responseCode: ResponseCode?,
    val responseLevel: LogType?
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

        fun fail(message: String, responseCode: ResponseCode): CommonResponse<Nothing> {
            return CommonResponse(
                ResponseResult.FAIL,
                null,
                message,
                responseCode,
                responseCode.logType
            )
        }

        fun fail(responseCode: ResponseCode): CommonResponse<Nothing> {
            return CommonResponse(
                ResponseResult.FAIL,
                null,
                responseCode.message,
                responseCode,
                responseCode.logType
            )
        }

        fun <T> fail(responseCode: ResponseCode, data: T?): CommonResponse<T> {
            return CommonResponse(
                ResponseResult.FAIL,
                data,
                responseCode.message,
                responseCode,
                responseCode.logType
            )
        }

        fun <T> fail(message: String, responseCode: ResponseCode, data: T?): CommonResponse<T> {
            return CommonResponse(
                ResponseResult.FAIL,
                data,
                message,
                responseCode,
                responseCode.logType
            )
        }

    }
}
