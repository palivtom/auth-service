package cz.cvut.fel.pivchart.auth.utils

import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher

object URLMatchers {
    val AUTH_API = OrRequestMatcher(AntPathRequestMatcher("/api/**/auth/**"))
    val ROLES_API = OrRequestMatcher(AntPathRequestMatcher("/api/**/roles"))
}