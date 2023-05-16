package wa.lab.server.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder


@Component
class KeycloakLogoutHandler() : LogoutHandler {
    private val restTemplate: RestTemplate = RestTemplate()
    override fun logout(
        request: HttpServletRequest?, response: HttpServletResponse?,
        auth: Authentication?
    ) {
        if (auth != null) {
            logoutFromKeycloak(auth.principal as OidcUser)
        }
    }

    private fun logoutFromKeycloak(user: OidcUser) {
        val endSessionEndpoint = user.issuer.toString() + "/protocol/openid-connect/logout"
        val builder = UriComponentsBuilder
            .fromUriString(endSessionEndpoint)
            .queryParam("id_token_hint", user.idToken.tokenValue)
        val logoutResponse = restTemplate.getForEntity(
            builder.toUriString(), String::class.java
        )
        if (logoutResponse.statusCode.is2xxSuccessful) {
                println("Successfully logged out from Keycloak")
        } else {
                println("Could not propagate logout to Keycloak")
        }
    }

}