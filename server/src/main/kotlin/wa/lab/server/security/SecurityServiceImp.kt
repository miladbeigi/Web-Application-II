package wa.lab.server.security

import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.representations.AccessTokenResponse
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wa.lab.server.profiles.ProfileServiceImp
import javax.ws.rs.BadRequestException
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.Response


@Service
class SecurityServiceImp : SecurityService {

    @Autowired
    private lateinit var keycloakProperties: KeycloakProperties

    @Autowired
    lateinit var profileService: ProfileServiceImp

    override fun login(username: String, password: String): ResponseEntity<Any> {
        val keycloak: Keycloak = KeycloakBuilder.builder()
            .realm(keycloakProperties.realm)
            .serverUrl(keycloakProperties.authServerUrl)
            .clientId(keycloakProperties.resource)
            .username(username)
            .password(password).build()

        var accessTokenResponse: AccessTokenResponse? = null
        return try {
            accessTokenResponse = keycloak.tokenManager().accessToken
            ResponseEntity.status(HttpStatus.OK).body<Any>(accessTokenResponse)
        } catch (ex: BadRequestException) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body<Any>(accessTokenResponse)
        }
    }

    @Transactional
    override fun signup(email: String, name: String, lastname: String, password: String?, role: String?): String {

        val keycloak: Keycloak = KeycloakBuilder.builder()
            .serverUrl(keycloakProperties.authServerUrl)
            .grantType(OAuth2Constants.PASSWORD)
            .realm(keycloakProperties.adminRealm)
            .clientId(keycloakProperties.adminClient)
            .username(keycloakProperties.adminUsername)
            .password(keycloakProperties.adminPassword)
            .resteasyClient(ClientBuilder.newBuilder().build()).build()

        val userRepresentation = UserRepresentation()
        userRepresentation.username = email
        userRepresentation.email = email
        userRepresentation.isEnabled = true
        userRepresentation.isEmailVerified = true

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

        if (response.status != 201) {
            throw RuntimeException("Error creating user in Keycloak")
        }

        // Assign the user to the role if role is not null
        if (role != null) {
            val roleRepresentation = realmResource.roles().get(role).toRepresentation()
            val userId = realmResource.users().searchByEmail(email, true)[0].id
            realmResource.users().get(userId).roles().realmLevel().add(listOf(roleRepresentation))
        }

        return response.statusInfo.toString()

    }
}