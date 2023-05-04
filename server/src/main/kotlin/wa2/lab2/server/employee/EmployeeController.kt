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
        value = ["/expert/add"],
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
        value = ["/manager/add"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun addManager(@RequestBody
                   @Valid
                   expert: ManagerDTO): ManagerDTO? {
        return employeeService.addManager(
            name = expert.name,
            lastname = expert.lastname,
            email = expert.email
        )
    }
}