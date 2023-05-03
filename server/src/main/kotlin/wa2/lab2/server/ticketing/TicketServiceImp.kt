package wa2.lab2.server.ticketing

import org.springframework.stereotype.Service
import wa2.lab2.server.products.ProductService
import wa2.lab2.server.profiles.ProfileService
import wa2.lab2.server.products.exceptions.ProductNotFoundException
import wa2.lab2.server.products.toEntity
import wa2.lab2.server.profiles.exceptions.ProfileNotFoundException
import wa2.lab2.server.profiles.toEntity
import wa2.lab2.server.ticketing.exceptions.TicketExceptions
import java.time.LocalDate
import java.time.LocalTime

@Service
class TicketServiceImp(
    private val ticketRepository: TicketRepository,
    private val ticketHistoryRepository: TicketHistoryRepository,
    private val productService: ProductService,
    private val profileService: ProfileService
) : TicketService {

    fun saveTicketHistory(
        ticket: Ticket,
        previousStatus: TicketStatus?,
        newStatus: TicketStatus,
    ) {
        val ticketHistory = TicketHistory(
            ticket = ticket,
            previousStatus = previousStatus,
            newStatus = newStatus,
            date = LocalDate.now(),
            time = LocalTime.now()
        )
        ticketHistoryRepository.save(ticketHistory)
    }

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
        saveTicketHistory(ticket, null, savedTicket.status)

        return savedTicket.id
    }

    override fun startTicket(ticketId: String): Long? {
        // Check if the ticket exists
        val ticket: Ticket? = ticketRepository.findById(ticketId.toLong()).orElse(null)
            ?: throw TicketExceptions("Ticket with id $ticketId not found")

        // Update the ticket status only if it is open or reopened
        if (ticket != null) {
            if (ticket.status == TicketStatus.Open || ticket.status == TicketStatus.ReOpened) {
                val oldStatus: TicketStatus = ticket.status
                ticket.status = TicketStatus.InProgress

                val updatedTicket = ticketRepository.save(ticket)
                saveTicketHistory(ticket, oldStatus, updatedTicket.status)

                return updatedTicket.id
            } else throw TicketExceptions("Ticket with id $ticketId cannot be started")
        }
        return null
    }

    override fun stopTicket(ticketId: String): Long? {
        // Check if the ticket exists
        val ticket: Ticket? = ticketRepository.findById(ticketId.toLong()).orElse(null)
            ?: throw TicketExceptions("Ticket with id $ticketId not found")

        // Update the ticket status only if it is in progress
        if (ticket != null) {
            if (ticket.status == TicketStatus.InProgress) {
                val oldStatus: TicketStatus = ticket.status
                ticket.status = TicketStatus.Open
                val updatedTicket = ticketRepository.save(ticket)
                saveTicketHistory(ticket, oldStatus, updatedTicket.status)
                return updatedTicket.id
            } else throw TicketExceptions("Ticket with id $ticketId cannot be stopped")
        }
        return null
    }

    override fun closeTicket(ticketId: String): Long? {
        // Check if the ticket exists
        val ticket: Ticket? = ticketRepository.findById(ticketId.toLong()).orElse(null)
            ?: throw TicketExceptions("Ticket with id $ticketId not found")

        // Ticket status can be closed from any other status
        if (ticket != null) {
            if (ticket.status != TicketStatus.Closed) {
                val oldStatus: TicketStatus = ticket.status
                ticket.status = TicketStatus.Closed
                val updatedTicket = ticketRepository.save(ticket)
                saveTicketHistory(ticket, oldStatus, updatedTicket.status)
                return updatedTicket.id
            } else throw TicketExceptions("Ticket with id $ticketId cannot be closed")
        }
        return null
    }

    override fun reopenTicket(ticketId: String): Long? {
        // Check if the ticket exists
        val ticket: Ticket? = ticketRepository.findById(ticketId.toLong()).orElse(null)
            ?: throw TicketExceptions("Ticket with id $ticketId not found")

        // Update the ticket status only if it is closed or resolved
        if (ticket != null) {
            if (ticket.status == TicketStatus.Closed || ticket.status == TicketStatus.Resolved) {
                val oldStatus: TicketStatus = ticket.status
                ticket.status = TicketStatus.ReOpened
                val updatedTicket = ticketRepository.save(ticket)
                saveTicketHistory(ticket, oldStatus, updatedTicket.status)
                return updatedTicket.id
            } else throw TicketExceptions("Ticket with id $ticketId cannot be reopened")
        }
        return null
    }

    override fun resolveTicket(ticketId: String): Long? {
        // Check if the ticket exists
        val ticket: Ticket? = ticketRepository.findById(ticketId.toLong()).orElse(null)
            ?: throw TicketExceptions("Ticket with id $ticketId not found")

        // Update the ticket status only if it is in open, reopened and in progress
        if (ticket != null) {
            if (ticket.status == TicketStatus.Open ||
                ticket.status == TicketStatus.ReOpened ||
                ticket.status == TicketStatus.InProgress
            ) {
                val oldStatus: TicketStatus = ticket.status
                ticket.status = TicketStatus.Resolved
                val updatedTicket = ticketRepository.save(ticket)
                saveTicketHistory(ticket, oldStatus, updatedTicket.status)
                return updatedTicket.id
            } else throw TicketExceptions("Ticket with id $ticketId cannot be resolved")
        }
        return null
    }
}