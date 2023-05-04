package wa2.lab2.server.ticketing

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class TicketDTO(
    val id: String,
    val title: String,
    val description: String,
    @field: NotEmpty()
    @field: NotNull()
    val productId: String,
    var status: String,
    @field: NotEmpty()
    @field: NotNull()
    val profileId: String,
    val priority: String?,
    val expert: String?
)

fun Ticket.toDTO(): TicketDTO? {
    return TicketDTO(
        id = id.toString(),
        title = title,
        description = description,
        productId = product.ean,
        status = status.name,
        profileId = profile.email,
        priority = priority?.name,
        expert = expert?.email
    )
}