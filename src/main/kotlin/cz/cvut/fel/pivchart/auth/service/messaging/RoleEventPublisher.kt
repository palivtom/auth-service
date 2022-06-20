package cz.cvut.fel.pivchart.auth.service.messaging

import cz.cvut.fel.pivchart.auth.model.messaging.RoleEvent
import cz.cvut.fel.pivchart.auth.model.messaging.RoleEventData
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component


@Component
class RoleEventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher
) : RoleEventPublisherI {

    override fun fire(messageData: RoleEventData) {
        val customSpringEvent = RoleEvent(this, messageData)
        applicationEventPublisher.publishEvent(customSpringEvent)
    }

}