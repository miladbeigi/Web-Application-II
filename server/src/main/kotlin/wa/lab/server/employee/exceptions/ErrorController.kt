package wa.lab.server.employee.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class EmployeeErrorController: ResponseEntityExceptionHandler() {
    @ExceptionHandler(EmployeeExceptions::class)
    fun handleProductNotFound(e: EmployeeExceptions) = ProblemDetail
        .forStatusAndDetail( HttpStatus.INTERNAL_SERVER_ERROR, e.message )
}