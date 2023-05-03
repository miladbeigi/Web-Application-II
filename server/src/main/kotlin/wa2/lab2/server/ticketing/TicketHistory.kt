package wa2.lab2.server.ticketing

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