package cz.cvut.fel.pivchart.auth.security.handler

import cz.cvut.fel.pivchart.auth.model.oauth.GoogleUserInfo
import cz.cvut.fel.pivchart.auth.security.JwtUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomSimpleUrlAuthenticationSuccessHandler(
    private val jwtUtil: JwtUtil
): SimpleUrlAuthenticationSuccessHandler() {
    companion object {
        private const val MAX_COOKIE_AGE = 300
        private const val DOMAIN_COOKIE_PATH = "/"
    }

    @Value("\${frontend.domain}")
    private val frontendDomain: String? = null

    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        val oidcUser = authentication?.principal as OidcUser
        val googleUserInfo = GoogleUserInfo(oidcUser.attributes)
        val responseTokens = jwtUtil.generateTokens(googleUserInfo)

        response?.apply {
            this.addCookie(cookieFactory.invoke("access_token", responseTokens.accessToken))
            this.addCookie(cookieFactory.invoke("refresh_token", responseTokens.refreshToken))
            this.sendRedirect("http://$frontendDomain:3000/auth/callback")
        }
    }

    private val cookieFactory = { name: String, value: String ->
        Cookie(name, value).apply {
            this.domain = frontendDomain
            this.maxAge = MAX_COOKIE_AGE
            this.path = DOMAIN_COOKIE_PATH
        }
    }
/*
    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        val oidcUser = authentication?.principal as OidcUser
        val responseEntity = resolve(oidcUser)

        val json = objectMapper.writeValueAsString(responseEntity.body)
        response?.apply {
            this.contentType = "application/json;charset=UTF-8"
            this.writer.write(json)
        }
    }

    private fun resolve(oidcUser: OidcUser): ResponseEntity<AccessRefreshTokensResponse> {
        val googleUserInfo = GoogleUserInfo(oidcUser.attributes)
        val response = jwtUtil.generateTokens(googleUserInfo)
        return ResponseEntity(response, HttpStatus.OK)
    }
 */
}