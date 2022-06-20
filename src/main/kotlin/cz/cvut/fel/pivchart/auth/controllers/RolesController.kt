package cz.cvut.fel.pivchart.auth.controllers

import cz.cvut.fel.pivchart.auth.exception.api.ForbiddenException
import cz.cvut.fel.pivchart.auth.model.request.RoleRequest
import cz.cvut.fel.pivchart.auth.model.response.RoleResponse
import cz.cvut.fel.pivchart.auth.service.interfaces.RoleServiceI
import cz.cvut.fel.pivchart.auth.service.interfaces.UserServiceI
import cz.cvut.fel.pivchart.auth.utils.ApiURL.API_V1
import cz.cvut.fel.pivchart.auth.utils.Auth
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("$API_V1/roles")
class RolesController(
    private val userService: UserServiceI,
    private val roleService: RoleServiceI,
    private val auth: Auth
) {

    @GetMapping
    fun getRoles(@RequestParam email: String): ResponseEntity<RoleResponse> {
        val user = userService.getUser(email)

        val response = RoleResponse(
            email = user.email,
            roles = roleService.getUserRoleTypes(user)
        )
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping
    fun addRoles(@RequestBody request: RoleRequest): ResponseEntity<RoleResponse> {
        if (auth.getEmail() == request.email) throw ForbiddenException("Cannot add roles to ourself.")

        val user = userService.getUser(request.email)
        roleService.addRoles(user, request.roles)

        val response = RoleResponse(
            email = user.email,
            roles = roleService.getUserRoleTypes(user)
        )
        return ResponseEntity(response, HttpStatus.OK)
    }

    @DeleteMapping
    fun removeRoles(@RequestBody request: RoleRequest): ResponseEntity<RoleResponse> {
        if (auth.getEmail() == request.email) throw ForbiddenException("Cannot remove roles to ourself.")

        val user = userService.getUser(request.email)
        roleService.removeRoles(user, request.roles)

        val response = RoleResponse(
            email = user.email,
            roles = roleService.getUserRoleTypes(user)
        )
        return ResponseEntity(response, HttpStatus.OK)
    }
}