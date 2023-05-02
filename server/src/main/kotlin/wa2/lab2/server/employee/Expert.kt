package wa2.lab2.server.employee

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

@Entity
data class Expert(
    @GeneratedValue
    @Id
    val id: Long,
    val name: String,
    val lastname: String,
    val email: String,
    @OneToOne
    val manager: Manager?,
    val expertise: Expertise
)


enum class Expertise {
    Software, Hardware, Network
}