package wa.lab.server.ticketing

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class TicketHistory(
    @GeneratedValue
    @Id
    val id: Long? = null,
    @OneToOne
    val ticket: Ticket,
    val previousStatus: TicketStatus? = null,
    val newStatus: TicketStatus,
    val date: LocalDate,
    val time: LocalTime
)

data class TicketHistoryDTO(
    val id: Long? = null,
    val ticket: String,
    val previousStatus: String? = null,
    val newStatus: String? = null,
    val date: String,
    val time: String
)

fun TicketHistory.toDTO(): TicketHistoryDTO {
    return TicketHistoryDTO(
        id = id,
        ticket = ticket.id.toString(),
        previousStatus = previousStatus?.name,
        newStatus = newStatus.name,
        date = date.toString(),
        time = time.toString()
    )
}