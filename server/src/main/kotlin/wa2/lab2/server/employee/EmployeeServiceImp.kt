package wa2.lab2.server.employee

import org.springframework.stereotype.Service
import wa2.lab2.server.employee.exceptions.EmployeeExceptions
import wa2.lab2.server.ticketing.TicketPriority
import wa2.lab2.server.ticketing.exceptions.TicketExceptions

@Service
class EmployeeServiceImp (
    val expertRepository: ExpertRepository,
    val managerRepository: ManagerRepository
) : EmployeeService {
    override fun addExpert(
        name: String,
        lastname: String,
        email: String,
        managerId: String?,
        expertise: String
    ): ExpertDTO {

        // Check if manager exists
        val manager : Manager? = if (managerId == null) throw EmployeeExceptions("Manager id is not valid") else {
            managerRepository.findById(managerId.toLong()).orElse(null)
        }

        // Set the expertise based on the values in enum
        val expertise : Expertise = when (expertise) {
            "1" -> Expertise.Hardware
            "2" -> Expertise.Software
            "3" -> Expertise.Network
            else -> throw EmployeeExceptions("Expertise $expertise is not valid")
        }

        // Create expert
        val expert = Expert(
            id = 0,
            name = name,
            lastname = lastname,
            email = email,
            manager = manager,
            expertise = expertise
        )
        // Save expert
        expertRepository.save(expert)

        // Return expert
        return expert.toDTO()
    }

    override fun getExpert(id: String?): Expert? {
        // Check if expert exists
        if (id == null) throw EmployeeExceptions("Expert id is not valid")
        return expertRepository.findById(id.toLong()).orElse(null) ?: return null
    }
    override fun addManager(name: String, lastname: String, email: String): ManagerDTO? {
        // Check if manager exists by email
        if (managerRepository.findByEmail(email).isPresent) {
            throw EmployeeExceptions("Manager with email $email already exists")
        }

        // Create manager
        val manager = Manager(
            name = name,
            lastname = lastname,
            email = email
        )
        // Save manager
        val savedManager = managerRepository.save(manager)
        // Return manager id
        return savedManager.toDTO()
    }
}