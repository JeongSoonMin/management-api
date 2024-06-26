package com.jesomi.management.global.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@ConfigurationProperties("application.aws.s3")
class S3Properties {
    lateinit var cdnBucket: S3BucketProperties
}

class S3BucketProperties {
    lateinit var bucketName: String
    lateinit var uploadPath: S3BucketUploadPAthProperties
}

class S3BucketUploadPAthProperties {
    lateinit var event: String
}