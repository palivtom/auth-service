package cz.cvut.fel.pivchart.auth.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator.Builder
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import cz.cvut.fel.pivchart.auth.model.response.AccessRefreshTokensResponse
import cz.cvut.fel.pivchart.auth.model.oauth.GoogleUserInfo
import cz.cvut.fel.pivchart.auth.service.RoleService
import cz.cvut.fel.pivchart.auth.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil(
    private val userService: UserService,
    private val roleService: RoleService
) {
    companion object {
        private const val ACCESS_EXP = 1000L * 60 * 5
        private const val REFRESH_EXP = 1000L * 60 * 60 * 24 * 30
        private const val APP_NAME = "pivchart"
    }

    @Value("\${jwt.secret}")
    private val secret: String? = null

    private val algorithm
        get() = Algorithm.HMAC256(secret)
    private val verifier
        get() = JWT.require(algorithm).withIssuer(APP_NAME).build()

    fun generateTokens(claims: GoogleUserInfo): AccessRefreshTokensResponse {
        val user = userService.getUser(claims.email)
        val roles = roleService.getUserRoleTypes(user).map {
            it.name
        }
        val creation = Date()

        return AccessRefreshTokensResponse(
            accessToken = createAccessToken(creation, user.email, roles),
            refreshToken = createRefreshToken(creation, user.email)
        )
    }

    fun validateToken(accessToken: String): DecodedJWT {
        return verifier.verify(accessToken)
    }

    fun generateFromRefreshToken(refreshToken: DecodedJWT): String {
        val user = userService.getUser(refreshToken.subject)
        val roles = roleService.getUserRoleTypes(user).map {
            it.name
        }
        val creation = Date()

        return createAccessToken(creation, user.email, roles)
    }

    private fun createAccessToken(creation: Date, email: String, roles: List<String>): String {
        val exception = Date(creation.time + ACCESS_EXP)
        val builderBase = createTokenBuilder(creation, exception, email)

        return builderBase
            .withClaim("roles", roles.toList())
            .sign(algorithm)
    }

    private fun createRefreshToken(creation: Date, email: String): String {
        val exception = Date(creation.time + REFRESH_EXP)
        val builderBase = createTokenBuilder(creation, exception, email)

        return builderBase.sign(algorithm)
    }

    private fun createTokenBuilder(creation: Date, expiration: Date, email: String): Builder {
        return JWT.create()
            .withSubject(email)
            .withIssuedAt(creation)
            .withExpiresAt(expiration)
            .withIssuer(APP_NAME)
    }
}