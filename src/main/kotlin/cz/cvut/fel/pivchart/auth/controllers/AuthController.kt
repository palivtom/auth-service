package cz.cvut.fel.pivchart.auth.controllers

import cz.cvut.fel.pivchart.auth.model.request.AccessTokenRequest
import cz.cvut.fel.pivchart.auth.model.request.JWTValidateRequest
import cz.cvut.fel.pivchart.auth.model.response.AccessTokenResponse
import cz.cvut.fel.pivchart.auth.security.JwtUtil
import cz.cvut.fel.pivchart.auth.utils.ApiURL.API_V1
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$API_V1/auth")
class AuthController(
    private val jwtUtil: JwtUtil
) {

    @PostMapping("/refresh-token")
    fun refreshToken(@RequestBody request: AccessTokenRequest): ResponseEntity<AccessTokenResponse> {
        val requestToken = jwtUtil.validateToken(request.refreshToken)
        val accessToken = jwtUtil.generateFromRefreshToken(requestToken)
        val response = AccessTokenResponse(accessToken)

        return ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping("/validate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun validate(@RequestBody request: JWTValidateRequest) {
        jwtUtil.validateToken(request.accessToken)
    }
}
