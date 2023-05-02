package wa2.lab2.server.employee

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class Manager(
    @GeneratedValue
    @Id
    val id: Long,
    val name: String,
    val lastname: String,
    val email: String
)
