package wa.lab.server.profiles

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull


data class ProfileDTO(
    @field: NotNull(message = "Email is required")
    val email: String,

    @field: NotEmpty(message = "Name is required")
    @field: NotNull(message = "Name is required")
    val name: String,

    @field: NotEmpty(message = "Lastname is required")
    @field: NotNull(message = "Lastname is required")
    val lastname: String,
    val password: String? = null
)

fun Profile.toDTO(): ProfileDTO {
    return ProfileDTO(email, name, lastname, password)
}

fun ProfileDTO.toEntity(): Profile {
    return Profile(email, name, lastname, password)
}