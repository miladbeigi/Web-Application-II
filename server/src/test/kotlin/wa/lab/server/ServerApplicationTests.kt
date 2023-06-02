package wa.lab.server

import org.junit.jupiter.api.AfterEach

import org.junit.jupiter.api.BeforeEach
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
import wa.lab.server.employee.exceptions.EmployeeExceptions
import wa.lab.server.products.ProductRepository
import wa.lab.server.products.ProductServiceImp
import wa.lab.server.products.exceptions.ProductNotFoundException
import wa.lab.server.profiles.ProfileRepository
import wa.lab.server.profiles.ProfileServiceImp
import wa.lab.server.profiles.exceptions.ProfileNotFoundException
import wa.lab.server.ticketing.*
import wa.lab.server.ticketing.exceptions.TicketExceptions

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
    lateinit var expertRepository: wa.lab.server.employee.ExpertRepository

    @Autowired
    lateinit var managerRepository: wa.lab.server.employee.ManagerRepository

    @Autowired
    lateinit var ticketService: TicketServiceImp

    @Autowired
    lateinit var profileService: ProfileServiceImp

    @Autowired
    lateinit var productService: ProductServiceImp

    @Autowired
    lateinit var employeeService: wa.lab.server.employee.EmployeeServiceImp

    @Test
    fun contextLoads() {
    }

    @BeforeEach
    fun initDatabase() {
        productService.createProduct(
            ean = "1234567890100", name = "IPhone 12", brand = "Apple"
        )
        profileService.createProfile(
            email = "milad.be@gmail.com", name = "Milad", lastname = "Beigi"
        )
        employeeService.addManager(
            name = "Milad", lastname = "B", email = "milad.be@gmail.com")

        employeeService.addExpert(
            name = "Milad", lastname = "B", email = "milad.be@gmail.com", "", "1"
        )


    }

    @AfterEach
    fun cleanDatabase() {
        ticketHistoryRepository.deleteAll()
        ticketRepository.deleteAll()
        productRepository.deleteAll()
        profileRepository.deleteAll()
        expertRepository.deleteAll()
        managerRepository.deleteAll()
    }

    @Test
    fun createProfile() {
        profileService.createProfile(
            email = "milad@gmail.com", name = "Milad", lastname = "B"
        )
    }

    @Test
    fun createProduct() {
        productService.createProduct(
            ean = "1234567890101", name = "Test product", brand = "IPhone 6"
        )
    }

    @Test
    fun createTicket() {
        assertThrows<ProfileNotFoundException> {
            ticketService.createTicket(
                title = "Test ticket",
                description = "Test ticket description",
                productId = "1234567890100",
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
            productId = "1234567890100",
            profileId = "milad.be@gmail.com"
        )

        assert(ticketId != null)
    }

    @Test
    fun startTicket() {
        val ticketId: Long? = ticketService.createTicket(
            title = "Test ticket",
            description = "Test ticket description",
            productId = "1234567890100",
            profileId = "milad.be@gmail.com"
        )

        assertThrows<TicketExceptions> { ticketService.startTicket(ticketId.toString()) }

        ticketService.stopTicket(ticketId.toString())
        ticketService.startTicket(ticketId.toString())

        assert(ticketRepository.findById(ticketId!!).get().status == TicketStatus.InProgress)
    }

    @Test
    fun stopTicket() {
        val ticketId: Long? = ticketService.createTicket(
            title = "Test ticket",
            description = "Test ticket description",
            productId = "1234567890100",
            profileId = "milad.be@gmail.com"
        )

        ticketService.stopTicket(ticketId.toString())
        assertThrows<TicketExceptions> { ticketService.stopTicket(ticketId.toString()) }
        assert(ticketRepository.findById(ticketId!!).get().status == TicketStatus.Open)
    }

    @Test
    fun closeTicket() {
        val ticketId: Long? = ticketService.createTicket(
            title = "Test ticket",
            description = "Test ticket description",
            productId = "1234567890100",
            profileId = "milad.be@gmail.com"
        )

        ticketService.closeTicket(ticketId.toString())
        assertThrows<TicketExceptions> { ticketService.closeTicket(ticketId.toString()) }
        assert(ticketRepository.findById(ticketId!!).get().status == TicketStatus.Closed)
    }

    @Test
    fun reopenTicket() {
        val ticketId: Long? = ticketService.createTicket(
            title = "Test ticket",
            description = "Test ticket description",
            productId = "1234567890100",
            profileId = "milad.be@gmail.com"
        )

        ticketService.closeTicket(ticketId.toString())
        ticketService.reopenTicket(ticketId.toString())
        assertThrows<TicketExceptions> { ticketService.reopenTicket(ticketId.toString()) }
        assert(ticketRepository.findById(ticketId!!).get().status == TicketStatus.ReOpened)

    }

    @Test
    fun resolveTicket() {
        val ticketId: Long? = ticketService.createTicket(
            title = "Test ticket",
            description = "Test ticket description",
            productId = "1234567890100",
            profileId = "milad.be@gmail.com"
        )

        ticketService.resolveTicket(ticketId.toString())
        assertThrows<TicketExceptions> { ticketService.resolveTicket(ticketId.toString()) }
        assert(ticketRepository.findById(ticketId!!).get().status == TicketStatus.Resolved)
    }

    @Test
    fun assignTicket() {
        val ticketId: Long? = ticketService.createTicket(
            title = "Test ticket",
            description = "Test ticket description",
            productId = "1234567890100",
            profileId = "milad.be@gmail.com"
        )

        // Empty expert
        assertThrows<Exception> {
            ticketService.assignTicket(
                ticketId.toString(),
                title = "Test ticket",
                description = "Test ticket description",
                expert = "",
                priority = "1"
            )
        }

        // Empty priority
        assertThrows<Exception> {
            ticketService.assignTicket(
                ticketId.toString(),
                title = "Test ticket",
                description = "Test ticket description",
                expert = "1",
                priority = ""
            )
        }

        // Get expert
        val expertId: Long? = expertRepository.findByEmail("milad.be@gmail.com")[0].id

        ticketService.assignTicket(
            ticketId.toString(),
            title = "Test ticket",
            description = "Test ticket description",
            expert = expertId.toString(),
            priority = "1"
        )

        // Assert ticket is assigned
        assert(ticketRepository.findById(ticketId!!).get().expert != null)
        assert(ticketRepository.findById(ticketId!!).get().priority == TicketPriority.Low)


    }

    @Test
    fun addExpert() {
        employeeService.addExpert(
            name = "Alan", lastname = "Turing", email = "alan.turing@gmail.com", "", "1"
        )

        // repeated email
        assertThrows<EmployeeExceptions> {
            employeeService.addExpert(
                name = "a", lastname = "t", email = "alan.turing@gmail.com", "", "1"
            )
        }

        // empty expertise
        assertThrows<EmployeeExceptions> {
            employeeService.addExpert(
                name = "a", lastname = "t", email = "alan.t@gmail.com", "", ""
            )
        }

        // expertise does not exist
        assertThrows<EmployeeExceptions> {
            employeeService.addExpert(
                name = "a", lastname = "t", email = "alan.t@gmail.com", "", "4"
            )
        }

        // manager id does not exist
        assertThrows<EmployeeExceptions> {
            employeeService.addExpert(
                name = "a", lastname = "t", email = "alan.t@gmail.com", "99", ""
            )
        }

    }

    @Test
    fun addManager() {
        employeeService.addManager(
            name = "Tim", lastname = "Berners-Lee", email = "tim.b@gmail.com")

        // repeated email address
        assertThrows<EmployeeExceptions> {
            employeeService.addExpert(
                name = "t", lastname = "b", email = "tim.b@gmail.com", "99", ""
            )
        }
    }

}
