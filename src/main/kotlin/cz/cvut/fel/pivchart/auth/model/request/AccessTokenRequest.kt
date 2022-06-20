package cz.cvut.fel.pivchart.auth.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class AccessTokenRequest(
    @JsonProperty("refreshToken")
    val refreshToken: String
)