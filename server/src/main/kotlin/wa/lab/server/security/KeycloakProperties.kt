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
    lateinit var adminUsername: String
    lateinit var adminPassword: String
    lateinit var adminClient: String
    lateinit var adminRealm : String
}