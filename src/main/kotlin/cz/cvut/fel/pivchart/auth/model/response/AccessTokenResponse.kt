package cz.cvut.fel.pivchart.auth.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class AccessTokenResponse(
    @JsonProperty("accessToken")
    val accessToken: String
)