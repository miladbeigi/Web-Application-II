package wa2.lab2.server.profiles.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ProfileErrorController: ResponseEntityExceptionHandler() {
    @ExceptionHandler(ProfileAlreadyExistsException::class)
    fun handleProductNotFound(e: ProfileAlreadyExistsException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.INTERNAL_SERVER_ERROR, e.message )
    @ExceptionHandler(ProfileNotFoundException::class)
    fun handleProductNotFound(e: ProfileNotFoundException) = ProblemDetail
        .forStatusAndDetail( HttpStatus.NOT_FOUND, e.message )
}