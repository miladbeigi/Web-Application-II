package wa2.lab2.server.employee

interface EmployeeService {
    fun addExpert(
        name: String,
        lastname: String,
        email: String,
        managerId: String?,
        expertise: Expertise
    ): Long

    fun addManager(
        name: String,
        lastname: String,
        email: String
    ): Long

}