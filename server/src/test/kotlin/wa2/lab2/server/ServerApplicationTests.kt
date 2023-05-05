package wa2.lab2.server

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import wa2.lab2.server.products.ProductRepository
import wa2.lab2.server.products.ProductServiceImp
import wa2.lab2.server.products.exceptions.ProductNotFoundException
import wa2.lab2.server.profiles.ProfileRepository
import wa2.lab2.server.profiles.ProfileServiceImp
import wa2.lab2.server.profiles.exceptions.ProfileNotFoundException
import wa2.lab2.server.ticketing.TicketHistoryRepository
import wa2.lab2.server.ticketing.TicketRepository
import wa2.lab2.server.ticketing.TicketServiceImp
import wa2.lab2.server.ticketing.TicketStatus
import wa2.lab2.server.ticketing.exceptions.TicketExceptions

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ServerApplicationTests {
    companion object {
        @Container

        val postgres = PostgreSQLContainer("postgres:latest")

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
            registry.add("spring.jpa.hibernate.ddl-auto") { "create-drop" }
        }
    }

    @Autowired
    lateinit var ticketRepository: TicketRepository

    @Autowired
    lateinit var profileRepository: ProfileRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var ticketHistoryRepository: TicketHistoryRepository

    @Autowired
    lateinit var ticketService: TicketServiceImp

    @Autowired
    lateinit var profileService: ProfileServiceImp

    @Autowired
    lateinit var productService: ProductServiceImp

    @Test
    fun contextLoads() {
    }

    @Test
    fun createProfile() {
        profileService.createProfile(
            email = "milad@gmail.com", name = "M", lastname = "B"
        )
    }

    @Test
    fun createProduct() {
        productService.createProduct(
            ean = "1234567890122", name = "Test product", brand = "IPhone 6"
        )
    }

    @Test
    fun createTicket() {
        productService.createProduct(
            ean = "1234567890123", name = "IPhone 12", brand = "Apple"
        )
        profileService.createProfile(
            email = "milad.be@gmail.com", name = "M", lastname = "B"
        )

        assertThrows<ProfileNotFoundException> {
            ticketService.createTicket(
                title = "Test ticket",
                description = "Test ticket description",
                productId = "1234567890123",
                profileId = ""
            )
        }

        assertThrows<ProductNotFoundException> {
            ticketService.createTicket(
                title = "Test ticket",
                description = "Test ticket description",
                productId = "",
                profileId = "milad.be@gmail.com"
            )
        }

        val ticketId: Long? = ticketService.createTicket(
            title = "Test ticket",
            description = "Test ticket description",
            productId = "1234567890123",
            profileId = "milad.be@gmail.com"
        )

        assert(ticketId != null)
    }

    // TODO: Add tests for the following functions
    @Test
    fun startTicket() {
        productService.createProduct(
            ean = "1234567890113", name = "IPhone 12", brand = "Apple"
        )

        profileService.createProfile(
            email = "m@gmail.com", name = "M", lastname = "B"
        )

        val ticketId: Long? = ticketService.createTicket(
            title = "Test ticket",
            description = "Test ticket description",
            productId = "1234567890113",
            profileId = "m@gmail.com"
        )

        assertThrows<TicketExceptions> { ticketService.startTicket(ticketId.toString()) }

        ticketService.stopTicket(ticketId.toString())
        ticketService.startTicket(ticketId.toString())

        assert(ticketRepository.findById(ticketId!!).get().status == TicketStatus.InProgress)
    }

    @Test
    fun stopTicket() {
        productService.createProduct(
            ean = "1234567890114", name = "IPhone 12", brand = "Apple"
        )
        profileService.createProfile(
            email = "mb@gmail.com", name = "M", lastname = "B"
        )

        val ticketId: Long? = ticketService.createTicket(
            title = "Test ticket",
            description = "Test ticket description",
            productId = "1234567890114",
            profileId = "mb@gmail.com"
        )

        ticketService.stopTicket(ticketId.toString())
        assertThrows<TicketExceptions> { ticketService.stopTicket(ticketId.toString()) }
        assert(ticketRepository.findById(ticketId!!).get().status == TicketStatus.Open)
    }

    @Test
    fun closeTicket() {
        assert(true)
    }

    @Test
    fun reopenTicket() {
        assert(true)
    }

    @Test
    fun resolveTicket() {
        assert(true)
    }

    @Test
    fun assignTicket() {
        assert(true)
    }
    @Test
    fun addExpert() {
        assert(true)
    }

    @Test
    fun addManager(){
        assert(true)
    }

}
