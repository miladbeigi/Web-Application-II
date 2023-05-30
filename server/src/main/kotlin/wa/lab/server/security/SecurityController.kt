package wa.lab.server.security
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import wa.lab.server.profiles.ProfileDTO

@RestController
class SecurityController (private val securityService: SecurityService) {

    @Autowired
    private lateinit var keycloakProperties: KeycloakProperties

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        return securityService.handleLogin(loginRequest.username, loginRequest.password)
    }


    @GetMapping("/login")
    fun login(response: HttpServletResponse) {
        val keycloakUrl = "${keycloakProperties.authServerUrl}/realms/${keycloakProperties.realm}/protocol/openid-connect/auth"
        val redirectUri = keycloakProperties.callback
        val redirectUrl = "$keycloakUrl?" +
                "response_type=code&" +
                "client_id=${keycloakProperties.resource}&" +
                "redirect_uri=$redirectUri"

        response.sendRedirect(redirectUrl)
    }

    @PostMapping(
        value = ["/signup"],
        consumes = ["application/json"],
        produces = ["application/json"])
    fun signup(@RequestBody @Valid profile: ProfileDTO): String {
        return securityService.signup(profile.email, profile.name, profile.lastname, profile.password)
    }
}

data class LoginRequest(val username: String, val password: String)