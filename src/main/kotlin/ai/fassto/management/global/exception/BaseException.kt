package ai.fassto.management.global.exception

import ai.fassto.management.global.enums.ErrorCode

open class BaseException : RuntimeException {
    override val message: String?
        get() = super.message
    var data: Any? = null
    open var errorCode: ErrorCode = ErrorCode.UNPROCESSABLE_ENTITY

    constructor() : super()

    constructor(message: String?) : super(message)

    constructor(message: String?, data: Any?) : super(message){
        this.data = data
    }

    constructor(message: String?, data: Any?, errorCode: ErrorCode) : super(message){
        this.data = data
    }

    constructor(errorCode: ErrorCode) : super() {
        this.errorCode = errorCode
    }

    constructor(errorCode: ErrorCode, data: Any?) : super() {
        this.errorCode = errorCode
        this.data = data
    }
}