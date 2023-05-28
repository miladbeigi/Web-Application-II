package wa.lab.server.security

interface SecurityService {
    fun handleLogin(username : String, password : String) : String
    fun signup(email: String, name: String, lastname: String, password: String?) : String
}