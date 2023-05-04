package wa2.lab2.server.employee

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class ManagerDTO(
    val id: String,
    val name: String,
    val lastname: String,
    @field: NotEmpty()
    @field: NotNull()
    val email: String
)

fun Manager.toDTO(): ManagerDTO {
    return ManagerDTO(id.toString(), name, lastname, email)
}
