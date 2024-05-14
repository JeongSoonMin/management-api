package com.jesomi.management.global.exception

import com.jesomi.management.global.enums.ResponseCode

open class BaseException : RuntimeException {
    override val message: String?
        get() = super.message
    var data: Any? = null
    open var responseCode: ResponseCode = ResponseCode.UNPROCESSABLE_ENTITY

    constructor() : super()

    constructor(message: String?) : super(message)

    constructor(message: String?, data: Any?) : super(message){
        this.data = data
    }

    constructor(message: String?, data: Any?, responseCode: ResponseCode) : super(message){
        this.data = data
    }

    constructor(responseCode: ResponseCode) : super() {
        this.responseCode = responseCode
    }

    constructor(responseCode: ResponseCode, data: Any?) : super() {
        this.responseCode = responseCode
        this.data = data
    }
}