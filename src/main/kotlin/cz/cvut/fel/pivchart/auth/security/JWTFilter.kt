package cz.cvut.fel.pivchart.auth.security

import com.auth0.jwt.exceptions.JWTVerificationException
import com.fasterxml.jackson.databind.ObjectMapper
import cz.cvut.fel.pivchart.auth.model.response.ErrorResponse
import cz.cvut.fel.pivchart.auth.service.RoleService
import cz.cvut.fel.pivchart.auth.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JWTFilter(
    private val userService: UserService,
    private val roleService: RoleService,
    private val objectMapper: ObjectMapper,
    private val jwtUtil: JwtUtil
) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader != null && authHeader.isNotBlank() && authHeader.startsWith("Bearer ")) {
            val jwt = authHeader.substring(7)
            try {
                SecurityContextHolder.getContext().authentication = processAuthToken(jwt)
            } catch (e: JWTVerificationException) {
                val errorBody = ErrorResponse(
                    message = e.message,
                )

                val json = objectMapper.writeValueAsString(errorBody)
                response.apply {
                    this.contentType = "application/json;charset=UTF-8"
                    this.status = HttpStatus.UNAUTHORIZED.value()
                    this.writer.write(json)
                }
                return
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun processAuthToken(jwt: String) : Authentication {
        val jwtDecoded = jwtUtil.validateToken(jwt)
        val email = jwtDecoded.subject
        val user = userService.getUser(email)
        val authorities = roleService.getUserRoleTypes(user).map {
            SimpleGrantedAuthority(it.name)
        }

        return UsernamePasswordAuthenticationToken(email, null, authorities)
    }
}