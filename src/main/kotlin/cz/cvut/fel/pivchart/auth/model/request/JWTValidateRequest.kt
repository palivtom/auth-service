package cz.cvut.fel.pivchart.auth.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class JWTValidateRequest(
    @JsonProperty("accessToken")
    val accessToken: String
)