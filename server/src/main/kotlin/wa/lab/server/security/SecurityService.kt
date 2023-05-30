package wa.lab.server.security

import org.springframework.http.ResponseEntity

interface SecurityService {
    fun handleLogin(username : String, password : String) : ResponseEntity<Any>
    fun signup(email: String, name: String, lastname: String, password: String?) : String
}