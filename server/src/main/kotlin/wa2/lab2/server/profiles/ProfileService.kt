package wa2.lab2.server.profiles

interface ProfileService {
    fun getProfileByEmail(email: String): ProfileDTO?
}