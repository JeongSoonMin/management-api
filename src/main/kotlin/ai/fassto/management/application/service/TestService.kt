package ai.fassto.management.application.service

import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Slf4j
@Service
class TestService {
    val logger: Logger = LoggerFactory.getLogger(TestService::class.java)

    @Async
    fun virtualThreadTest() {
        logger.info("threadId : {}", Thread.currentThread().threadId())
        logger.info("isVirtual : {}", Thread.currentThread().isVirtual)
        Thread.sleep(3000)
        logger.info("Thread Complete")
    }
}