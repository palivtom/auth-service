package cz.cvut.fel.pivchart.auth.model.messaging

import cz.cvut.fel.pivchart.auth.model.enums.RoleEventOperation
import java.time.LocalDateTime

data class RoleEventData(
    val sourceUser: String,
    val targetUser: String,
    val operation: RoleEventOperation,
    val body: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)