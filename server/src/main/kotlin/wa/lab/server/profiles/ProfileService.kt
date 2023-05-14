package wa.lab.server.profiles

interface ProfileService {
    fun getProfileByEmail(email: String): ProfileDTO?
    fun createProfile(email: String, name: String, lastname: String): ProfileDTO
    fun updateProfile(email: String, name: String, lastname: String): ProfileDTO
}