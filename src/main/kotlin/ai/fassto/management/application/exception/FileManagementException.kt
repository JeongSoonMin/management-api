package ai.fassto.management.application.exception

import ai.fassto.management.global.enums.ResponseCode
import ai.fassto.management.global.exception.BaseException

class FileManagementException : BaseException {
    override var responseCode: ResponseCode = ResponseCode.FILE_MANAGEMENT_SERVER_ERROR

    constructor() : super()

    constructor(message: String?) : super(message)

    constructor(message: String?, data: Any?) : super(message){
        this.data = data
    }

    constructor(responseCode: ResponseCode) : this(responseCode.message) {
        this.responseCode = responseCode
    }

    constructor(responseCode: ResponseCode, data: Any?) : this(responseCode.message) {
        this.responseCode = responseCode
        this.data = data
    }
}