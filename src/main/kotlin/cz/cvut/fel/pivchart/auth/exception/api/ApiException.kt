package cz.cvut.fel.pivchart.auth.exception.api

import org.springframework.http.HttpStatus

abstract class ApiException (
    val reason: String,
    val httpStatus: HttpStatus
) : RuntimeException()