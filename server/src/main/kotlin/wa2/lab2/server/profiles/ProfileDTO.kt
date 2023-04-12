package wa2.lab2.server.profiles

data class ProfileDTO(
    val email: String,
    val name: String,
    val lastname: String
)

fun Profile.toDTO(): ProfileDTO {
    return ProfileDTO(email, name, lastname)
}