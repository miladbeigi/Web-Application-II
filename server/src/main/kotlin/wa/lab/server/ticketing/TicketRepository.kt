package wa.lab.server.ticketing

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TicketRepository : JpaRepository<Ticket, Long> {
}

@Repository
interface TicketHistoryRepository : JpaRepository<TicketHistory, Long> {
}