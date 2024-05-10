package ai.fassto.management.application.listener

import ai.fassto.management.global.common.log
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.sqs.annotation.SqsListener
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component

@Component
@Slf4j
class AmazonSQSListener {
    val log = this.log()
    val om = ObjectMapper()


//    @SqsListener(value = ["\${application.aws.sqs.test-queue}"])
    fun emailBounceProcess(message: String?, acknowledgement: Acknowledgement) {
        log.info("[Queue 수신 처리] message : {}", message)

//        var messageDTO: MessageDTO = MessageDTO()
//
//        try {
//            messageDTO = om.readValue(message, MessageDTO::class.java)
//        } catch (e: JsonProcessingException) {
//            log.error("[Exception] ", e)
//        }
//
//        log.info("[Queue 수신 변환] messageDTO : {}", messageDTO)

        acknowledgement.acknowledge() // 수신 된 메시지 ack 처리.
    }


}