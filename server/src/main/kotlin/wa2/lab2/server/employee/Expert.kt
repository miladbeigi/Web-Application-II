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


fun Expert.toDTO(): ExpertDTO {
    return ExpertDTO(
        id = this.id,
        name = this.name,
        lastname = this.lastname,
        email = this.email,
        manager = manager?.email,
        expertise = expertise.name
    )
}
enum class Expertise {
    Software, Hardware, Network
}