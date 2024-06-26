package com.jesomi.management.global.configuration

import io.awspring.cloud.autoconfigure.sqs.SqsProperties
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory
import io.awspring.cloud.sqs.listener.ListenerMode
import io.awspring.cloud.sqs.listener.QueueNotFoundStrategy
import io.awspring.cloud.sqs.listener.SqsContainerOptionsBuilder
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.internal.crt.S3CrtAsyncClient
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import java.time.Duration
import java.time.temporal.ChronoUnit

@Configuration
class AmazonConfig {

    @Bean
    fun amazonS3Client(): S3AsyncClient {
        return S3CrtAsyncClient.builder()
            .build()
    }

    @Bean
    fun amazonS3PreSigner(): S3Presigner {
        return S3Presigner.builder()
            .build()
    }

    @Bean
    fun defaultSqsListenerContainerFactory(): SqsMessageListenerContainerFactory<Any> {
        return SqsMessageListenerContainerFactory
            .builder<Any>()
            .configure { options: SqsContainerOptionsBuilder ->
                options
                    .acknowledgementMode(AcknowledgementMode.MANUAL) // 서비스 내에서 acknowledge 처리 하도록 수동 설정.
                    .listenerMode(ListenerMode.SINGLE_MESSAGE)
                    .maxConcurrentMessages(100)
                    .maxMessagesPerPoll(100)
                    .listenerShutdownTimeout(Duration.of(25L, ChronoUnit.SECONDS))
                    .acknowledgementShutdownTimeout(Duration.of(20L, ChronoUnit.SECONDS))
                    .acknowledgementThreshold(5)
                    .acknowledgementInterval(Duration.of(50, ChronoUnit.MILLIS))
                    .queueNotFoundStrategy(QueueNotFoundStrategy.FAIL)
            }
            .sqsAsyncClient(sqsAsyncClient())
            .build()
    }

    @Bean
    fun sqsAsyncClient(): SqsAsyncClient {
        return SqsAsyncClient.builder().build()
    }

    @Bean
    fun listener(): SqsProperties.Listener {
        return SqsProperties.Listener()
    }
}