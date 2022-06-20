package cz.cvut.fel.pivchart.auth.repository

import cz.cvut.fel.pivchart.auth.model.entity.Role
import cz.cvut.fel.pivchart.auth.model.entity.User
import cz.cvut.fel.pivchart.auth.model.enums.RoleType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RoleRepository : JpaRepository<Role, Long> {
    @Query("select r from Role r where r.roleType in :roleType")
    fun findRoles(roleType: Set<RoleType>): MutableSet<Role>

//    @Query("select r from Role r where r.users = :user")
    fun findByUsers(user: User): MutableSet<Role>
}