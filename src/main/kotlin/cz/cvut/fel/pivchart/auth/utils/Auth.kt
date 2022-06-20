package cz.cvut.fel.pivchart.auth.utils

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class Auth {
    fun getEmail() : String {
        val authorization = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken
        return authorization.principal as String
    }
}