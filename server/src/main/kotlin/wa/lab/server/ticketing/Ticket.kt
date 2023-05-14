package wa.lab.server.ticketing

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import wa.lab.server.employee.Expert
import wa.lab.server.products.Product
import wa.lab.server.profiles.Profile

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