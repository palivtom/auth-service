package cz.cvut.fel.pivchart.auth.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class AccessRefreshTokensResponse(
    @JsonProperty("accessToken")
    val accessToken: String,
    @JsonProperty("refreshToken")
    val refreshToken: String
)