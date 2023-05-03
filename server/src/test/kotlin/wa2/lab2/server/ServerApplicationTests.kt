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

@Testcontainers
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
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

    @Test
    fun contextLoads() {
    }

    @Test
    fun createProfile() {
        val profileService = ProfileServiceImp(profileRepository)
        profileService.createProfile(
            email = "milad@gmail.com",
            name = "M",
            lastname = "B")
    }
    @Test
    fun createProduct() {
        val productService = ProductServiceImp(productRepository)
        productService.createProduct(
            ean = "1234567890122",
            name = "Test product",
            brand = "IPhone 6")
    }

    @Test
    fun createTicket() {

        val productService = ProductServiceImp(productRepository)
        productService.createProduct(
            ean = "1234567890123",
            name = "IPhone 12",
            brand = "Apple")

        val profileService = ProfileServiceImp(profileRepository)
        profileService.createProfile(
            email = "milad.be@gmail.com",
            name = "M",
            lastname = "B")

        val ticketService = TicketServiceImp(
            ticketRepository,
            ticketHistoryRepository,
            productService,
            profileService
        )

        assertThrows<ProfileNotFoundException> {
            ticketService.createTicket(
                title = "Test ticket",
                description = "Test ticket description",
                productId = "1234567890123",
                profileId = "")
        }

        assertThrows<ProductNotFoundException> {
            ticketService.createTicket(
                title = "Test ticket",
                description = "Test ticket description",
                productId = "",
                profileId = "milad.be@gmail.com")
        }

        val ticketId: Long? = ticketService.createTicket(
            title = "Test ticket",
            description = "Test ticket description",
            productId = "1234567890123",
            profileId = "milad.be@gmail.com")

        assert(ticketId != null)
    }

    // TODO: Add tests for the following functions
    @Test
    fun startTicket() {
        assert(true)
    }
    @Test
    fun stopTicket() {
        assert(true)
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

}
