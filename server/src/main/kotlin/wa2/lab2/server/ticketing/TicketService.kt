package wa2.lab2.server.ticketing

import org.springframework.stereotype.Service


interface TicketService {
    fun createTicket(
        title: String,
        description: String,
        productId: String,
        profileId: String
    ): Long?
    fun startTicket(
        ticketId: String

    ) : Long?
    fun stopTicket(
        ticketId: String
    ) : Long?
    fun closeTicket(
        ticketId: String
    ) : Long?
    fun reopenTicket(
        ticketId: String
    ) : Long?
    fun resolveTicket(
        ticketId: String
    ) : Long?
}