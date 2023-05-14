package wa.lab.server.employee

import org.springframework.stereotype.Service
import wa.lab.server.employee.exceptions.EmployeeExceptions


@Service
class EmployeeServiceImp (
    val expertRepository: wa.lab.server.employee.ExpertRepository,
    val managerRepository: wa.lab.server.employee.ManagerRepository
) : wa.lab.server.employee.EmployeeService {
    @OptIn(ExperimentalStdlibApi::class)
    override fun addExpert(
        name: String,
        lastname: String,
        email: String,
        managerId: String?,
        expertise: String
    ): wa.lab.server.employee.ExpertDTO {

        // Check if expert exists by email
        if (expertRepository.findByEmail(email).toList().isNotEmpty()) {
            throw EmployeeExceptions("Expert with email $email already exists")
        }

        // Check if manager exists by id and id is not null
        val manager : wa.lab.server.employee.Manager? = if (managerId.isNullOrEmpty()) null else {
            managerRepository.findById(managerId.toLong()).orElse(null) ?: throw EmployeeExceptions("Manager with id $managerId does not exist")
        }

        // Set the expertise based on the values in enum
        val expertise : wa.lab.server.employee.Expertise = when (expertise) {
            "1" -> wa.lab.server.employee.Expertise.Hardware
            "2" -> wa.lab.server.employee.Expertise.Software
            "3" -> wa.lab.server.employee.Expertise.Network
            else -> throw EmployeeExceptions("Expertise $expertise is not valid")
        }

        // Create expert
        val expert = wa.lab.server.employee.Expert(
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

    override fun getExpert(id: String?): wa.lab.server.employee.Expert? {
        // Check if expert exists
        if (id.isNullOrEmpty()) throw EmployeeExceptions("Expert id is not valid")
        return expertRepository.findById(id.toLong()).orElse(null) ?: return null
    }
    override fun addManager(name: String, lastname: String, email: String): wa.lab.server.employee.ManagerDTO? {
        // Check if manager exists by email
        if (managerRepository.findByEmail(email).toList().isNotEmpty()) {
            throw EmployeeExceptions("Manager with email $email already exists")
        }

        // Create manager
        val manager = wa.lab.server.employee.Manager(
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