package wa2.lab2.server.ticketing

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Ticket(
    @Id
    val id: Long,
    val title: String,
    val description: String,
    val productId: String,
    val status: TicketStatus = TicketStatus.InProgress,
    val profileId: String,
    val priority: TicketPriority?,
    val expertId: String?
)

enum class TicketStatus {
    Open, InProgress, Closed, Resolved, ReOpened
}

enum class TicketPriority {
    Low, Medium, High
}