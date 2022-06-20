package cz.cvut.fel.pivchart.auth.service.interfaces

import cz.cvut.fel.pivchart.auth.model.entity.User
import cz.cvut.fel.pivchart.auth.model.enums.RoleType
import cz.cvut.fel.pivchart.auth.model.oauth.GoogleUserInfo

interface UserServiceI {

    /**
     * Gets persisted user by email.
     * It is just redirecting repository layer.
     */
    fun getUser(email: String): User

    /**
     * Gets true value if user exists by email.
     * It is just redirecting repository layer.
     */
    fun userExists(email: String): Boolean


    /**
     * Creates user by Google oauth2 principal attributes and with given RoleTypes.
     */
    fun createUser(claims: GoogleUserInfo, roleTypes: Set<RoleType>): User
}