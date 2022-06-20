package cz.cvut.fel.pivchart.auth.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import cz.cvut.fel.pivchart.auth.model.enums.RoleType

data class RoleRequest(
    @JsonProperty("email")
    val email: String,
    @JsonProperty("roles")
    val roles: Set<RoleType>
)
