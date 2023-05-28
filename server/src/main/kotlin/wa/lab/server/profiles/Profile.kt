package wa.lab.server.profiles

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "profiles")
data class Profile (
    @Id
    var email: String = "",
    var name: String = "",
    var lastname: String = "",
    var password: String? = ""
)