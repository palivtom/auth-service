package cz.cvut.fel.pivchart.auth.service

import cz.cvut.fel.pivchart.auth.exception.api.NotFoundException
import cz.cvut.fel.pivchart.auth.model.entity.User
import cz.cvut.fel.pivchart.auth.model.enums.RoleType
import cz.cvut.fel.pivchart.auth.model.oauth.GoogleUserInfo
import cz.cvut.fel.pivchart.auth.repository.UserRepository
import cz.cvut.fel.pivchart.auth.service.interfaces.UserServiceI
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val roleService: RoleService
) : UserServiceI {
    override fun getUser(email: String): User {
        return userRepository.findByEmail(email)
            ?: throw NotFoundException("Could not find user with email = $email")
    }

    override fun userExists(email: String): Boolean {
        return userRepository.existsUserByEmail(email)
    }

    override fun createUser(claims: GoogleUserInfo, roleTypes: Set<RoleType>): User {
        val user = userRepository.save(
            User().apply {
                this.email = claims.email
            }
        )

        roleService.addRoles(user, roleTypes)
        return user
    }
}