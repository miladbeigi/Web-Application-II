package wa.lab.server.employee

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class ExpertDTO (
    val id: Long,
    @field: NotEmpty()
    @field: NotNull()
    val name: String,
    @field: NotEmpty()
    @field: NotNull()
    val lastname: String,
    @field: NotEmpty()
    @field: NotNull()
    val email: String,
    val manager: String?,
    val expertise: String
)