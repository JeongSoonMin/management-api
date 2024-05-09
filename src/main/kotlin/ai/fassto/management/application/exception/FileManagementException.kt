package ai.fassto.management.application.exception

import ai.fassto.management.global.enums.ErrorCode
import ai.fassto.management.global.exception.BaseException

class FileManagementException : BaseException {
    override var errorCode: ErrorCode = ErrorCode.FILE_MANAGEMENT_SERVER_ERROR

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