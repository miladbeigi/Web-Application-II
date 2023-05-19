package wa.lab.server.security


import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SecurityController (private val securityService: SecurityService) {

    @Autowired
    private lateinit var keycloakProperties: KeycloakProperties

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
}