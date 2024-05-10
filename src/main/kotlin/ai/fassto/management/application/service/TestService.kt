package ai.fassto.management.application.service

import ai.fassto.management.application.model.SampleDto
import ai.fassto.management.global.common.log
import lombok.extern.slf4j.Slf4j
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.util.concurrent.Executors


@Slf4j
@Service
class TestService(
    val sampleService: SampleService,
) {
    val log = this.log()

    @Async
    fun virtualThreadAsyncTest() {
        log.info("threadId : {}", Thread.currentThread().threadId())
        log.info("isVirtual : {}", Thread.currentThread().isVirtual)
        Thread.sleep(3000)
        log.info("Thread Complete")
    }

    fun virtualThreadTest() {
        // Virtual Thread 방법 1
        Thread.startVirtualThread {
            log.info("test1 isVirtual : {}", Thread.currentThread().isVirtual)
        }


        // Virtual Thread 방법 2
        val runnable = Runnable { log.info("runnable id, isVirtual : {}, {}", Thread.currentThread().threadId(), Thread.currentThread().isVirtual) }
        val virtualThread2 = Thread.ofVirtual().start(runnable)


        // Virtual Thread 이름 지정
        val builder: Thread.Builder = Thread.ofVirtual().name("JVM-Thread")
        val virtualThread3 = builder.start(runnable)


        // 스레드가 Virtual Thread인지 확인하여 출력(여기서는 톰캣 가상 thread 사용 됨.)
        log.info("Thread 3 is Virtual : {} ", virtualThread3.isVirtual)


        Executors.newVirtualThreadPerTaskExecutor().use { executorService ->
            for (i in 0..2) {
                executorService.submit(runnable)
            }
        }
    }

    @Cacheable(cacheManager = "testCacheManager", value = ["sample::list"])
    fun findSampleListCache(): SampleDto.SampleListResponse {
        val sampleList = sampleService.findAll(0, 20)
        log.info("db 조회 완료")
        return sampleList
    }
}