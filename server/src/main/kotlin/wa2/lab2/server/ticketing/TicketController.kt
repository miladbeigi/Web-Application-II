package wa2.lab2.server.ticketing

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

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
        value = ["/api/ticket/start"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun startTicket(@RequestBody @Valid ticket: TicketDTO): Long? {
        return ticketService.startTicket(ticket.id)
    }

    @PutMapping(
        value = ["/api/ticket/stop"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun stopTicket(@RequestBody @Valid ticket: TicketDTO): Long? {
        return ticketService.stopTicket(ticket.id)
    }

    @PutMapping(
        value = ["/api/ticket/close"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun closeTicket(@RequestBody @Valid ticket: TicketDTO): Long? {
        return ticketService.closeTicket(ticket.id)
    }

    @PutMapping(
        value = ["/api/ticket/reopen"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun reopenTicket(@RequestBody @Valid ticket: TicketDTO): Long? {
        return ticketService.reopenTicket(ticket.id)
    }

    @PutMapping(
        value = ["/api/ticket/resolve"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun resolveTicket(@RequestBody @Valid ticket: TicketDTO): Long?{
        return ticketService.resolveTicket(ticket.id)
    }
}