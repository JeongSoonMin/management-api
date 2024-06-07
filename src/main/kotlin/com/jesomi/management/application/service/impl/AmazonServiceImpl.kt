package com.jesomi.management.application.service.impl

import com.jesomi.management.application.service.AmazonService
import com.jesomi.management.global.constants.AmazonConst
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.HeadObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration

@Service
class AmazonServiceImpl(
    private val amazonS3Client: S3AsyncClient,
    private val amazonS3PreSigner: S3Presigner
) : AmazonService {
    override fun s3GeneratePreSignedUrl(bucketName: String, uploadFullPath: String): String {
        val preSignedRequest: PresignedPutObjectRequest = amazonS3PreSigner.presignPutObject(
            PutObjectPresignRequest.builder()
                .putObjectRequest(
                    PutObjectRequest.builder().bucket(bucketName).key(uploadFullPath).build()
                )
                .signatureDuration(Duration.ofMinutes(AmazonConst.PRE_SIGNED_URL_EXPIRE_TIME))
                .build()
        )
        return preSignedRequest.url().toExternalForm()
    }

    override fun s3DeleteObject(bucketName: String, uploadFullPath: String) {
        amazonS3Client.deleteObject(
            DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(uploadFullPath)
                .build()
        )
    }

    override fun isS3FileExist(bucketName: String, uploadFullPath: String): Boolean {
        try {
            // head 정보만 가져와서 체크 함.
            amazonS3Client.headObject(
                HeadObjectRequest.builder().bucket(bucketName).key(uploadFullPath).build()
            ).get()
            return true
        } catch (e: Exception) {
            // 404 Not Found의 경우 미존재로 AWS SDK 가이드 문서상에는 NoSuchKeyException 발생으로 처리 및 CompletableFuture 내부 메시지에는 NoSuchKeyException 으로 되어 있으나 ExecutionException 발생.
            return false
        }
    }
}