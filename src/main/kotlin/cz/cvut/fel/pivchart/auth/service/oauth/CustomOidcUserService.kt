package cz.cvut.fel.pivchart.auth.service.oauth

import cz.cvut.fel.pivchart.auth.model.enums.RoleType
import cz.cvut.fel.pivchart.auth.model.oauth.GoogleUserInfo
import cz.cvut.fel.pivchart.auth.service.interfaces.UserServiceI
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service
import java.util.Collections

@Service
class CustomOidcUserService(
    private val userService: UserServiceI
) : OidcUserService() {

    /**
     * Creates user with default roles if already not exists.
     */
    override fun loadUser(userRequest: OidcUserRequest?): OidcUser {
        val oidcUser = super.loadUser(userRequest)

        val claims = GoogleUserInfo(oidcUser.attributes)
        if (!userService.userExists(claims.email)) {
            userService.createUser(claims, Collections.singleton(RoleType.ROLE_USER))
        }

        return oidcUser
    }
}