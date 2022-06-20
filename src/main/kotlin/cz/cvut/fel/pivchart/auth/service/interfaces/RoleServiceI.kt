package cz.cvut.fel.pivchart.auth.service.interfaces

import cz.cvut.fel.pivchart.auth.model.entity.Role
import cz.cvut.fel.pivchart.auth.model.entity.User
import cz.cvut.fel.pivchart.auth.model.enums.RoleType

interface RoleServiceI {

    /**
     * If RoleType is not persisted, creates Role record with current RoleType. As a result is
     * list of persisted RoleTypes in database.
     */
    fun createOrGet(roleTypes: Set<RoleType>): Set<Role>

    /**
     * Gets users RoleTypes.
     * Use always this method instead of directly getting roles from User. This method is @Cacheable.
     */
    fun getUserRoleTypes(user: User): Set<RoleType>

    /**
     * Adds RoleTypes to user if user does not already have these roles.
     * Use always this method instead of directly getting roles from User. This method is @Cacheable.
     */
    fun addRoles(user: User, roleTypes: Set<RoleType>)

    /**
     * Removes RoleTypes to user if user has these roles.
     * Use always this method instead of directly getting roles from User. This method is @Cacheable.
     */
    fun removeRoles(user: User, roleTypes: Set<RoleType>)
}