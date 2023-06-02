package wa.lab.server.security

import org.springframework.http.ResponseEntity

interface SecurityService {
    fun login(username : String, password : String) : ResponseEntity<Any>
    fun signup(email: String, name: String, lastname: String, password: String?, role: String?) : String
}