package wa.lab.server.security

import org.springframework.stereotype.Service

@Service
class SecurityServiceImp : SecurityService {
    override fun handleLogin(username: String, password: String): String {
        return "Login successful"
    }
}