package wa.lab.server.security
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "keycloak")
class KeycloakProperties {
    lateinit var authServerUrl: String
    lateinit var realm: String
    lateinit var resource: String
    lateinit var callback: String
}