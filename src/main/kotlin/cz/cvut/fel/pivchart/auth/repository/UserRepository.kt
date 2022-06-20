package cz.cvut.fel.pivchart.auth.repository

import cz.cvut.fel.pivchart.auth.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun existsUserByEmail(email: String): Boolean
}