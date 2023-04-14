package wa2.lab2.server.profiles

interface ProfileService {
    fun getProfileByEmail(email: String): ProfileDTO?
    fun createProfile(email: String, name: String, lastname: String): ProfileDTO
    fun updateProfile(email: String, name: String, lastname: String): ProfileDTO
}