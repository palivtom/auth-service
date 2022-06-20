package cz.cvut.fel.pivchart.auth.config

import cz.cvut.fel.pivchart.auth.security.JWTFilter
import cz.cvut.fel.pivchart.auth.security.handler.CustomSimpleUrlAuthenticationSuccessHandler
import cz.cvut.fel.pivchart.auth.service.oauth.CustomOidcUserService
import cz.cvut.fel.pivchart.auth.utils.URLMatchers.AUTH_API
import cz.cvut.fel.pivchart.auth.utils.URLMatchers.ROLES_API
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SecurityConfig(
    private val customOidcUserService: CustomOidcUserService,
    private val customSimpleUrlAuthenticationSuccessHandler: CustomSimpleUrlAuthenticationSuccessHandler,
    private val jwtFilter: JWTFilter
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .cors()
            .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                    .requestMatchers(AUTH_API).permitAll()
                    .requestMatchers(ROLES_API).hasAuthority("ROLE_ADMIN")
                    .anyRequest().permitAll()
            .and()
                .oauth2Login()
                    .userInfoEndpoint()
                        .oidcUserService(customOidcUserService)
                .and()
                    .successHandler(customSimpleUrlAuthenticationSuccessHandler)
            .and()
                .exceptionHandling()
                    .authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}