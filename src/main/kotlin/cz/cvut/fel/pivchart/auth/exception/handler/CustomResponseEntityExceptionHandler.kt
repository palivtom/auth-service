package cz.cvut.fel.pivchart.auth.exception.handler

import com.auth0.jwt.exceptions.JWTVerificationException
import cz.cvut.fel.pivchart.auth.exception.api.ApiException
import cz.cvut.fel.pivchart.auth.model.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class CustomResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [ApiException::class])
    fun handleApiException(e: ApiException): ResponseEntity<ErrorResponse> {
        val errorBody = ErrorResponse(
            message = e.reason,
        )
        return ResponseEntity(errorBody, e.httpStatus)
    }

    @ExceptionHandler(value = [JWTVerificationException::class])
    fun handleJwtVerificationException(e: JWTVerificationException): ResponseEntity<ErrorResponse> {
        val errorBody = ErrorResponse(
            message = e.message,
        )
        return ResponseEntity(errorBody, HttpStatus.FORBIDDEN)
    }
}