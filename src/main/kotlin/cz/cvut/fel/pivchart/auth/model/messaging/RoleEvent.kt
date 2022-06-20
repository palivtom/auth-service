package cz.cvut.fel.pivchart.auth.model.messaging

import org.springframework.context.ApplicationEvent


class RoleEvent(
    source: Any,
    val roleEventData: RoleEventData
) : ApplicationEvent(source)