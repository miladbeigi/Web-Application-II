package wa2.lab2.server.ticketing

data class TicketDTO(
    val id: String,
    val title: String,
    val description: String,
    val productId: String,
    var status: String,
    val profileId: String,
    val priority: String?,
    val expert: String?
)
