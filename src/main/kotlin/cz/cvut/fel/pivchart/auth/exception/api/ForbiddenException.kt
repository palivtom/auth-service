package cz.cvut.fel.pivchart.auth.exception.api

import org.springframework.http.HttpStatus

class ForbiddenException(
    reason: String
) : ApiException (
    reason = reason,
    httpStatus = HttpStatus.FORBIDDEN
)