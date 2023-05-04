package wa2.lab2.server.ticketing.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class TicketErrorController: ResponseEntityExceptionHandler() {
    @ExceptionHandler(TicketExceptions::class)
    fun handleProductNotFound(e: TicketExceptions) = ProblemDetail
        .forStatusAndDetail( HttpStatus.INTERNAL_SERVER_ERROR, e.message )
}