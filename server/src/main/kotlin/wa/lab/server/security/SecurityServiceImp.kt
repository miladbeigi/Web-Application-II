package wa.lab.server.security

import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wa.lab.server.profiles.ProfileServiceImp
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.Response


@Service
class SecurityServiceImp : SecurityService {

    @Autowired
    private lateinit var keycloakProperties: KeycloakProperties

    var keycloak: Keycloak = KeycloakBuilder.builder()
        .serverUrl(keycloakProperties.authServerUrl)
        .grantType(OAuth2Constants.PASSWORD)
        .realm(keycloakProperties.adminRealm)
        .clientId(keycloakProperties.adminClient)
        .username(keycloakProperties.adminUsername)
        .password(keycloakProperties.adminPassword)
        .resteasyClient(ClientBuilder.newBuilder().build()).build()

    // create profile service
    @Autowired
    lateinit var profileService: ProfileServiceImp

    override fun handleLogin(username: String, password: String): String {
        val keycloak: Keycloak = KeycloakBuilder.builder()
            .realm(keycloakProperties.realm)
            .serverUrl(keycloakProperties.authServerUrl)
            .clientId(keycloakProperties.resource)
            .username(username)
            .password(password).build()

        return keycloak.tokenManager().accessToken.toString()
    }

    @Transactional
    override fun signup(email: String, name: String, lastname: String, password: String?): String {

        val userRepresentation = UserRepresentation()
        userRepresentation.username = email
        userRepresentation.email = email
        userRepresentation.isEnabled = true

        // Create CredentialRepresentation object and set the password
        val credential = CredentialRepresentation()
        credential.isTemporary = false
        credential.type = CredentialRepresentation.PASSWORD
        credential.value = password

        // Set the credentials for the user
        userRepresentation.credentials = listOf(credential)

        // Create the profile in the database
        profileService.createProfile(email, name, lastname)
        keycloak.tokenManager().accessToken
        val realmResource = keycloak.realm(keycloakProperties.realm)
        // Create the user in Keycloak
        val response: Response = realmResource.users().create(userRepresentation)
        println(response.statusInfo)
        println(response.status)
        println(response.location)

        return response.statusInfo.toString()

    }
}