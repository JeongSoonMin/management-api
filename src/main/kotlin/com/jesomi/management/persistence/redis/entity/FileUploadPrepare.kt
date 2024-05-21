package com.jesomi.management.persistence.redis.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.time.LocalDateTime
import java.util.*

@RedisHash(value = "management:file-upload-prepare")
class FileUploadPrepare(
    @Id
    val uploadId: UUID,
    val fileManageCd: String,
    val bucketName: String,
    val uploadPath: String,
    val uploadFileName: String,
    val originFileName: String,
    val regId: String,
    val regDate: LocalDateTime,
    @TimeToLive
    val ttl: Long
) {

}