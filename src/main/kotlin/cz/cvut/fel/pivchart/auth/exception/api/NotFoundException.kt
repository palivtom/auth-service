package cz.cvut.fel.pivchart.auth.exception.api

import org.springframework.http.HttpStatus

class NotFoundException(
    reason: String
) : ApiException (
    reason = reason,
    httpStatus = HttpStatus.NOT_FOUND
)