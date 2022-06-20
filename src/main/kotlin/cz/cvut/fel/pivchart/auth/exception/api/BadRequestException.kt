package cz.cvut.fel.pivchart.auth.exception.api

import org.springframework.http.HttpStatus

class BadRequestException(
    reason: String
) : ApiException (
    reason = reason,
    httpStatus = HttpStatus.BAD_REQUEST
)