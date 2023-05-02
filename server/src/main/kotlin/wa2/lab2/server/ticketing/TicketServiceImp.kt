package wa2.lab2.server.ticketing
import org.springframework.stereotype.Service
import wa2.lab2.server.products.ProductService
import wa2.lab2.server.profiles.ProfileService
import wa2.lab2.server.products.exceptions.ProductNotFoundException
import wa2.lab2.server.products.toEntity
import wa2.lab2.server.profiles.exceptions.ProfileNotFoundException
import wa2.lab2.server.profiles.toEntity
import wa2.lab2.server.ticketing.exceptions.TicketExceptions
import java.util.*

@Service
class TicketServiceImp(
    private val ticketRepository: TicketRepository,
    private val productService: ProductService,
    private val profileService: ProfileService
                ) : TicketService {
    override fun createTicket(
        title: String,
        description: String,
        productId: String,
        profileId: String
    ): Long? {
        // Check if the product exists
        val product = productService.getProductById(productId)
            ?: throw ProductNotFoundException("Product with ean $productId not found")

        // Check if profile exists
        val profile = profileService.getProfileByEmail(profileId)
            ?: throw ProfileNotFoundException("Profile with email $profileId not found")

        // Create the ticket
        val ticket = Ticket(
            title = title,
            description = description,
            product = product.toEntity(),
            profile = profile.toEntity(),
            status = TicketStatus.InProgress,
            priority = null,
            expert = null
        )

        // Save the ticket
        val savedTicket = ticketRepository.save(ticket)
        return savedTicket.id
    }

    override fun startTicket(ticketId: String) : Long? {
        // Check if the ticket exists
        var ticket : Ticket? = ticketRepository.findById(ticketId.toLong()).orElse(null)
            ?: throw TicketExceptions("Ticket with id $ticketId not found")

        // Update the ticket status only if it is open or reopened
        if (ticket != null) {
            if (ticket.status == TicketStatus.Open || ticket.status == TicketStatus.ReOpened) {
                ticket.status = TicketStatus.InProgress
                val updatedTicket = ticketRepository.save(ticket)
                return updatedTicket.id
            } else throw TicketExceptions("Ticket with id $ticketId cannot be started")
        }
        return null
    }

    override fun stopTicket(ticketId: String): Long? {
        // Check if the ticket exists
        var ticket : Ticket? = ticketRepository.findById(ticketId.toLong()).orElse(null)
            ?: throw TicketExceptions("Ticket with id $ticketId not found")

        // Update the ticket status only if it is in progress
        if (ticket != null) {
            if (ticket.status == TicketStatus.InProgress) {
                ticket.status = TicketStatus.Open
                val updatedTicket = ticketRepository.save(ticket)
                return updatedTicket.id
            } else throw TicketExceptions("Ticket with id $ticketId cannot be stopped")
        }
        return null
    }

    override fun closeTicket(ticketId: String): Long? {
        TODO("Not yet implemented")
    }

    override fun reopenTicket(ticketId: String): Long? {
        TODO("Not yet implemented")
    }

    override fun resolveTicket(ticketId: String): Long? {
        TODO("Not yet implemented")
    }
}