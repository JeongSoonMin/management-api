package ai.fassto.management.application.service

import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.util.concurrent.Executors


@Slf4j
@Service
class TestService {
    val logger: Logger = LoggerFactory.getLogger(TestService::class.java)

    @Async
    fun virtualThreadAsyncTest() {
        logger.info("threadId : {}", Thread.currentThread().threadId())
        logger.info("isVirtual : {}", Thread.currentThread().isVirtual)
        Thread.sleep(3000)
        logger.info("Thread Complete")
    }

    fun virtualThreadTest() {
        // Virtual Thread 방법 1
        Thread.startVirtualThread {
            logger.info("test1 isVirtual : {}", Thread.currentThread().isVirtual)
        }


        // Virtual Thread 방법 2
        val runnable = Runnable { logger.info("runnable id, isVirtual : {}, {}", Thread.currentThread().threadId(), Thread.currentThread().isVirtual) }
        val virtualThread2 = Thread.ofVirtual().start(runnable)


        // Virtual Thread 이름 지정
        val builder: Thread.Builder = Thread.ofVirtual().name("JVM-Thread")
        val virtualThread3 = builder.start(runnable)


        // 스레드가 Virtual Thread인지 확인하여 출력(여기서는 톰캣 가상 thread 사용 됨.)
        logger.info("Thread 3 is Virtual : {} ", virtualThread3.isVirtual)


        Executors.newVirtualThreadPerTaskExecutor().use { executorService ->
            for (i in 0..2) {
                executorService.submit(runnable)
            }
        }
    }
}