package com.jesomi.management.persistence.redis.entity

import com.jesomi.management.persistence.rdb.entity.FileUpload
import com.jesomi.management.persistence.redis.constants.RedisConst
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.time.LocalDateTime
import java.util.*

@RedisHash(value = "settlement-management:file-upload-prepare", timeToLive = RedisConst.FILE_MANAGEMENT_PREPARE_TTL)
class FileUploadPrepare(
    @Id
    val uploadId: UUID,
    val fileManageCd: String,
    val bucketName: String,
    val uploadPath: String,
    val uploadFileName: String,
    val originFileName: String,
    val regId: String,
    val regDate: LocalDateTime
) {
    fun toCmsFileUploadEntity(): FileUpload {
        return FileUpload(
            fileManageCd = this.fileManageCd,
            bucketName = this.bucketName,
            uploadPath = this.uploadPath,
            uploadFileName = this.uploadFileName,
            originFileName = this.originFileName,
            regId = this.regId
        )
    }
}