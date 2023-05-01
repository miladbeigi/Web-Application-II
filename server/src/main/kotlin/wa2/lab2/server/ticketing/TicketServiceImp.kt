package wa2.lab2.server.ticketing
import wa2.lab2.server.products.ProductService
import wa2.lab2.server.profiles.ProfileService
import wa2.lab2.server.products.exceptions.ProductNotFoundException
import wa2.lab2.server.profiles.exceptions.ProfileNotFoundException

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
    ): Long {
        // Check if the product exists
        val product = productService.getProductById(productId)
            ?: throw ProductNotFoundException("Product with ean $productId not found")

        // Check if profile exists
        val profile = profileService.getProfileByEmail(profileId)
            ?: throw ProfileNotFoundException("Profile with email $profileId not found")

        // Create the ticket
        val ticket = Ticket(
            id = 1,
            title = title,
            description = description,
            productId = product.ean,
            profileId = profile.email,
            status = TicketStatus.InProgress,
            priority = null,
            expertId = null
        )

        // Save the ticket
        val savedTicket = ticketRepository.save(ticket)
        return savedTicket.id
    }
}