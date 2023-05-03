package wa2.lab2.server.ticketing

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
class TicketController (private val ticketService: TicketService) {

    @PostMapping(
        value = ["/api/ticket/create"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun createTicket(@RequestBody @Valid ticket: TicketDTO): Long? {
        return ticketService.createTicket(
            ticket.title,
            ticket.description,
            ticket.productId,
            ticket.profileId
        )
    }

    @PutMapping(
        value = ["/api/ticket/start/{id}"],
        produces = ["application/json"]
    )
    fun startTicket(@PathVariable id: String): Long? {
        return ticketService.startTicket(id)
    }

    @PutMapping(
        value = ["/api/ticket/stop/{id}"],
        produces = ["application/json"]
    )
    fun stopTicket(@PathVariable id: String): Long? {
        return ticketService.stopTicket(id)
    }

    @PutMapping(
        value = ["/api/ticket/close/{id}"],
        produces = ["application/json"]
    )
    fun closeTicket(@PathVariable id: String): Long? {
        return ticketService.closeTicket(id)
    }

    @PutMapping(
        value = ["/api/ticket/reopen/{id}"],
        produces = ["application/json"]
    )
    fun reopenTicket(@PathVariable id: String): Long? {
        return ticketService.reopenTicket(id)
    }

    @PutMapping(
        value = ["/api/ticket/resolve/{id}"],
        produces = ["application/json"]
    )
    fun resolveTicket(@PathVariable id: String): Long?{
        return ticketService.resolveTicket(id)
    }
}