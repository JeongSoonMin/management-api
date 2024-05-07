package ai.fassto.management.service

import ai.fassto.management.annotation.SlaveDBTransactional
import ai.fassto.management.entity.Sample
import ai.fassto.management.persistence.master.SampleMasterRepository
import ai.fassto.management.persistence.slave.SampleSlaveRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import kotlin.random.Random

@Service
@Transactional(readOnly = true)
class SampleService(
    private val sampleMasterRepository: SampleMasterRepository,
    private val sampleSlaveRepository: SampleSlaveRepository
) {

    fun findAll(): List<Sample> {
        return sampleSlaveRepository.findAll().toList();
    }

    @SlaveDBTransactional
    fun saveAndFindAll(): List<Sample> {
        val sample = Sample(Random.nextLong(), "asdf1", LocalDateTime.now(), LocalDateTime.now());
        sampleMasterRepository.save(sample);
        Thread.sleep(1000);

//        throw RuntimeException("asdfasdf");

//        return sampleSlaveRepository.findAll().toList();
        return sampleMasterRepository.findBySampleName("test");
    }

    @Transactional
    fun save() {
        val sample = Sample(Random.nextLong(), "asdf1", LocalDateTime.now(), LocalDateTime.now());
        sampleMasterRepository.save(sample);
    }
}