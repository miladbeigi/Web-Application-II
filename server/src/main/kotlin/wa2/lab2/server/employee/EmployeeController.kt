package wa2.lab2.server.employee

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EmployeeController (
    val employeeService: EmployeeService
) {

    @PostMapping(
        value = ["/api/expert/add"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun addExpert(
        @RequestBody
        @Valid
        expert: ExpertDTO
    ): ExpertDTO? {
        return employeeService.addExpert(
            name = expert.name,
            lastname = expert.lastname,
            email = expert.email,
            managerId = expert.manager,
            expertise = expert.expertise
        )
    }
    @PostMapping(
        value = ["/api/manager/add"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun addManager(@RequestBody
                   @Valid
                   manager: ManagerDTO): ManagerDTO? {
        return employeeService.addManager(
            name = manager.name,
            lastname = manager.lastname,
            email = manager.email
        )
    }
}