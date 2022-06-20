package cz.cvut.fel.pivchart.auth.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import cz.cvut.fel.pivchart.auth.model.messaging.RoleEvent
import cz.cvut.fel.pivchart.auth.utils.KafkaTopics.ROLE_EVENT
import org.springframework.context.ApplicationListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component


@Component
class RoleEventKafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) : ApplicationListener<RoleEvent> {
    override fun onApplicationEvent(roleEvent: RoleEvent) {
        val jsonPayload = objectMapper.writeValueAsString(roleEvent.roleEventData)
        kafkaTemplate.send(ROLE_EVENT, jsonPayload)
    }
}