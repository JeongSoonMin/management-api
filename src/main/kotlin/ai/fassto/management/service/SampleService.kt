package ai.fassto.management.service

import ai.fassto.management.annotation.MasterDBTransactional
import ai.fassto.management.annotation.SlaveDBTransactional
import ai.fassto.management.entity.Sample
import ai.fassto.management.persistence.master.SampleMasterRepository
import ai.fassto.management.persistence.slave.SampleSlaveRepository
import org.springframework.stereotype.Service

@Service
@SlaveDBTransactional
class SampleService(
    private val sampleMasterRepository: SampleMasterRepository,
    private val sampleSlaveRepository: SampleSlaveRepository
) {

    fun findAll(): List<Sample> {
        return sampleSlaveRepository.findAll().toList();
    }


    @MasterDBTransactional
    fun save(sample: Sample): Sample {
        return sampleMasterRepository.save(sample);
    }
}