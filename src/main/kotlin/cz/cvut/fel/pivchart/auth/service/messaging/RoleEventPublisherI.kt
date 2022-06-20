package cz.cvut.fel.pivchart.auth.service.messaging

import cz.cvut.fel.pivchart.auth.model.messaging.RoleEventData

interface RoleEventPublisherI {

    /**
     * Executes the event through application publisher.
     */
    fun fire(messageData: RoleEventData)

}