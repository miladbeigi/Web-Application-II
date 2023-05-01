package wa2.lab2.server.ticketing

import java.time.LocalDate
import java.time.LocalTime

data class TicketHistory(
    val id: Int,
    val ticket: Ticket,
    val previousStatus: TicketStatus,
    val newStatus: TicketStatus,
    val date: LocalDate,
    val time: LocalTime
)
