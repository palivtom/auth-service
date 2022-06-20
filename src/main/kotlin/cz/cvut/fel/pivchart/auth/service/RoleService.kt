package cz.cvut.fel.pivchart.auth.service

import com.fasterxml.jackson.databind.ObjectMapper
import cz.cvut.fel.pivchart.auth.model.entity.Role
import cz.cvut.fel.pivchart.auth.model.entity.User
import cz.cvut.fel.pivchart.auth.model.enums.RoleEventOperation
import cz.cvut.fel.pivchart.auth.model.enums.RoleType
import cz.cvut.fel.pivchart.auth.model.messaging.RoleEventData
import cz.cvut.fel.pivchart.auth.repository.RoleRepository
import cz.cvut.fel.pivchart.auth.repository.UserRepository
import cz.cvut.fel.pivchart.auth.service.interfaces.RoleServiceI
import cz.cvut.fel.pivchart.auth.service.messaging.RoleEventPublisherI
import cz.cvut.fel.pivchart.auth.utils.Auth
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
@Transactional
class RoleService(
    private val roleRepository: RoleRepository,
    private val userRepository: UserRepository,
    private val roleEventPublisher: RoleEventPublisherI,
    private val objectMapper: ObjectMapper,
    private val auth: Auth
) : RoleServiceI {

    override fun createOrGet(roleTypes: Set<RoleType>): Set<Role> {
        val result = roleRepository.findRoles(roleTypes)
        val persistedRoleTypes = result.map { it.roleType }

        roleTypes.forEach {
            if (!persistedRoleTypes.contains(it)) {
                result.add(
                    roleRepository.save(
                        Role().apply {
                            roleType = it
                        }
                    )
                )
            }
        }

        return result
    }

    @Cacheable(key = "#user.email", value = ["rolesCache"])
    override fun getUserRoleTypes(user: User): Set<RoleType> {
        return roleRepository.findByUsers(user)
            .stream()
            .map { it.roleType }
            .collect(Collectors.toSet())
    }

    @CacheEvict(key = "#user.email", value = ["rolesCache"])
    override fun addRoles(user: User, roleTypes: Set<RoleType>) {
        val actuallRoleTypes = roleRepository.findByUsers(user).map { it.roleType }
        val toAddRoleTypes = roleTypes.stream()
            .filter { !actuallRoleTypes.contains(it) }
            .collect(Collectors.toSet())

        val rolesToAdd = createOrGet(toAddRoleTypes)

        roleEventPublisher.fire(
            RoleEventData(
                sourceUser = auth.getEmail(),
                targetUser = user.email,
                operation = RoleEventOperation.ADD,
                body = objectMapper.writeValueAsString(rolesToAdd.map { it.roleType })
            )
        )

        user.roles.addAll(rolesToAdd)
        userRepository.save(user)
    }

    @CacheEvict(key = "#user.email", value = ["rolesCache"])
    override fun removeRoles(user: User, roleTypes: Set<RoleType>) {
        val rolesToRemove: MutableSet<Role> = mutableSetOf()
        roleRepository.findByUsers(user).forEach {
            if (roleTypes.contains(it.roleType)) {
                rolesToRemove.add(it)
            }
        }

        roleEventPublisher.fire(
            RoleEventData(
                sourceUser = auth.getEmail(),
                targetUser = user.email,
                operation = RoleEventOperation.REMOVE,
                body = objectMapper.writeValueAsString(rolesToRemove.map { it.roleType })
            )
        )

        user.roles.removeAll(rolesToRemove)
        userRepository.save(user)
    }
}