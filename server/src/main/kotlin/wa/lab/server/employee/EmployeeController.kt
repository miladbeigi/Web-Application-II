package wa.lab.server.employee

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EmployeeController (
    val employeeService: wa.lab.server.employee.EmployeeService
) {

    @PostMapping(
        value = ["/createExpert"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun addExpert(
        @RequestBody
        @Valid
        expert: wa.lab.server.employee.ExpertDTO
    ): wa.lab.server.employee.ExpertDTO? {
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
                   manager: wa.lab.server.employee.ManagerDTO
    ): wa.lab.server.employee.ManagerDTO? {
        return employeeService.addManager(
            name = manager.name,
            lastname = manager.lastname,
            email = manager.email
        )
    }
}