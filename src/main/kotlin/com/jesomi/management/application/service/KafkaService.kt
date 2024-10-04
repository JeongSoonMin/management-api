package com.jesomi.management.application.service

interface KafkaService {
    fun send(topic: String, message: String)
}