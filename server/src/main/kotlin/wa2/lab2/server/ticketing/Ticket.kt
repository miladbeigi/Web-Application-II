package wa2.lab2.server.ticketing

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import wa2.lab2.server.employee.Expert
import wa2.lab2.server.products.Product
import wa2.lab2.server.profiles.Profile

@Entity
data class Ticket(
    @GeneratedValue
    @Id
    val id: Long? = null,
    val title: String,
    val description: String,
    @OneToOne
    val product: Product,
    var status: TicketStatus = TicketStatus.InProgress,
    @OneToOne
    val profile: Profile,
    val priority: TicketPriority?,
    @OneToOne
    val expert: Expert?
)
enum class TicketStatus {
    Open, InProgress, Closed, Resolved, ReOpened
}

enum class TicketPriority {
    Low, Medium, High
}