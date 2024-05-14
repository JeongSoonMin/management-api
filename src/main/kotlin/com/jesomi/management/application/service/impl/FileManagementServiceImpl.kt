package com.jesomi.management.application.service.impl

import com.jesomi.management.application.exception.FileManagementException
import com.jesomi.management.application.service.FileManagementService
import com.jesomi.management.global.common.log
import com.jesomi.management.global.configuration.properties.S3Properties
import com.jesomi.management.global.enums.ResponseCode
import com.jesomi.management.global.util.RandomUtil
import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class FileManagementServiceImpl(
    val amazonS3Client: AmazonS3,
    val s3Properties: S3Properties
): FileManagementService {
    val log = this.log()

    override fun generatePreSignedUrl(uploadType: String, fileName: String): String? {
        var bucketName: String? = null
        var uploadPath: String? = null

        if (uploadType == "event") {
            bucketName = s3Properties.cdnBucket.bucketName
            uploadPath = s3Properties.cdnBucket.uploadPath.event
        }

        if (bucketName == null || uploadPath == null) {
            throw FileManagementException(ResponseCode.NOT_SUPPORTED_UPLOAD_TYPE)
        }

        return generatePreSignedUrl(bucketName, uploadPath, fileName)
    }

    fun generatePreSignedUrl(bucketName: String, uploadPath: String, fileName: String): String? {
        val randomFileName = RandomUtil.getRandomNumAlpha(16)
        val fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)
        val uploadFullPath = "${uploadPath}${randomFileName}.${fileExt}"

        val expiration = Date()
        var expTimeMillis: Long = expiration.time
        expTimeMillis += (10 * 60 * 1000).toLong() // 10분
        expiration.time = expTimeMillis // url 만료 시간 설정

        val url = amazonS3Client.generatePresignedUrl(
            GeneratePresignedUrlRequest(bucketName, uploadFullPath)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration)
        )

        log.info("upload path >>> {}", url)

        return url.toString()
    }

}