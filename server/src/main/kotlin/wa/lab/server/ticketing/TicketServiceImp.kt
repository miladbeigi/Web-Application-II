package wa.lab.server.ticketing

import org.springframework.stereotype.Service
import wa.lab.server.employee.EmployeeService
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
    private val profileService: ProfileService,
    private val employeeService: wa.lab.server.employee.EmployeeService
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

    override fun assignTicket(
        ticketId: String,
        title: String,
        description: String,
        expert : String?,
        priority: String?
    ): TicketDTO? {

        if(ticketId.isNullOrEmpty()) throw TicketExceptions("Ticket id is not valid")
        // Check if the ticket exists
        val ticket: Ticket? = ticketRepository.findById(ticketId.toLong()).orElse(null)
            ?: throw TicketExceptions("Ticket with id $ticketId not found")

        // Check if expert exists
        val expertProfile = employeeService.getExpert(expert)
            ?: throw TicketExceptions("Expert with id $expert not found")

        // Set the priority according to the values in the enum
        val ticketPriority = when (priority) {
            "1" -> TicketPriority.Low
            "2" -> TicketPriority.Medium
            "3" -> TicketPriority.High
            else -> throw TicketExceptions("Priority $priority is not valid")
        }

        // Update the ticket
        if (ticket != null) {
            val newTicket = Ticket(
                id = ticket.id,
                title = title,
                description = description,
                product = ticket.product,
                status = ticket.status,
                profile = ticket.profile,
                priority = ticketPriority,
                expert = expertProfile
            )

            val updatedTicket = ticketRepository.save(newTicket)
            return updatedTicket.toDTO()
        }
        return null
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

    override fun getTicketHistory(ticketId: String): List<TicketHistoryDTO>? {
        // Check if id is valid
        if(ticketId.isNullOrEmpty()) throw TicketExceptions("Ticket id is not valid")

        // Check if the ticket exists
        val ticket: Ticket? = ticketRepository.findById(ticketId.toLong()).orElse(null)
            ?: throw TicketExceptions("Ticket with id $ticketId not found")

        // Get the ticket history
        val ticketHistory = ticketHistoryRepository.findAll().filter { it.ticket.id == ticket?.id }
        return ticketHistory.map { it.toDTO() }
    }
}