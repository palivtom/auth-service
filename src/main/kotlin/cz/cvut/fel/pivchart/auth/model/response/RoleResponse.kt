package cz.cvut.fel.pivchart.auth.model.response

import com.fasterxml.jackson.annotation.JsonProperty
import cz.cvut.fel.pivchart.auth.model.enums.RoleType

data class RoleResponse(
    @JsonProperty("email")
    val email: String,
    @JsonProperty("roles")
    val roles: Set<RoleType>
)