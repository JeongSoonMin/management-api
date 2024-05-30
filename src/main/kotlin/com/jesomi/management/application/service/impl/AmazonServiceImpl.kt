package com.jesomi.management.application.service.impl

import com.jesomi.management.application.service.AmazonService
import com.jesomi.management.global.constants.AmazonConst
import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class AmazonServiceImpl(
    private val amazonS3Client: AmazonS3
) : AmazonService {
    override fun s3GeneratePreSignedUrl(bucketName: String, uploadFullPath: String): String {
        val expiration = Date()
        var expTimeMillis: Long = expiration.time
        expTimeMillis += AmazonConst.PRE_SIGNED_URL_EXPIRE_TIME // 10분
        expiration.time = expTimeMillis // url 만료 시간 설정

        return amazonS3Client.generatePresignedUrl(
            GeneratePresignedUrlRequest(bucketName, uploadFullPath)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration)
        ).toString()
    }

    override fun s3DeleteObject(bucketName: String, uploadFullPath: String) {
        amazonS3Client.deleteObject(
            DeleteObjectRequest(
                bucketName,
                uploadFullPath
            )
        )
    }

    override fun isS3FileExist(bucketName: String, uploadFullPath: String): Boolean {
        return amazonS3Client.doesObjectExist(bucketName, uploadFullPath)
    }
}