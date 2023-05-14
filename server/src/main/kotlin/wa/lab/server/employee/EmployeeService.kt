package wa.lab.server.employee

interface EmployeeService {
    fun addExpert(
        name: String,
        lastname: String,
        email: String,
        managerId: String?,
        expertise: String
    ): wa.lab.server.employee.ExpertDTO

    fun getExpert(
        id: String?
    ): wa.lab.server.employee.Expert?

    fun addManager(
        name: String,
        lastname: String,
        email: String
    ): wa.lab.server.employee.ManagerDTO?
}