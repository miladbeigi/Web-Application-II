package wa.lab.server.ticketing



interface TicketService {
    fun createTicket(
        title: String,
        description: String,
        productId: String,
        profileId: String
    ): Long?

    fun assignTicket(
        ticketId: String,
        title: String,
        description: String,
        expert : String?,
        priority: String?
    ): TicketDTO?

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

    fun getTicketHistory(
        ticketId: String
    ) : List<TicketHistoryDTO>?
}