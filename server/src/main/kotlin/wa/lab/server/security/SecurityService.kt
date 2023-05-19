package wa.lab.server.security

interface SecurityService {
    fun handleLogin(username : String, password : String) : String
}