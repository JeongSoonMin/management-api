package com.jesomi.management.application.service.impl

import com.jesomi.management.application.service.KafkaService
import com.jesomi.management.global.common.log
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaServiceImpl(
    val kafkaTemplate: KafkaTemplate<String, String>
): KafkaService {
    val log = this.log()

    override fun send(topic: String, message: String) {
        kafkaTemplate.send(topic, message)
    }

    @KafkaListener(
        id = "management.test.sample.listener",
        topics = ["management.test.sample"],
    )
    fun testMessageListener(payload: String?) {
        log.info("kafka Listener Test Payload : {}", payload)
    }
}