package wa2.lab2.server.ticketing

interface TicketService {
    fun createTicket(
        title: String,
        description: String,
        productId: String,
        profileId: String
    ): Long

    fun ChangeTicketStatusTo(
        id: Long,
        newStatus: TicketStatus
    ): Long
}