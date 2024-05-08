package ai.fassto.management.global.exception

import ai.fassto.management.global.enums.ErrorCode

class BaseException : RuntimeException {
    override val message: String?
        get() = super.message
    var data: Any? = null
    var errorCode: ErrorCode = ErrorCode.INTERNAL_SERVER_ERROR

    constructor() : super()

    constructor(message: String?) : super(message)

    constructor(message: String?, data: Any?) : super(message){
        this.data = data
    }

    constructor(errorCode: ErrorCode) : this(errorCode.message) {
        this.errorCode = errorCode
    }

    constructor(errorCode: ErrorCode, data: Any?) : this(errorCode.message) {
        this.errorCode = errorCode
        this.data = data
    }
}