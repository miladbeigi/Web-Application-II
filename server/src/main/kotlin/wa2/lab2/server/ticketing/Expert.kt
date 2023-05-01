package wa2.lab2.server.ticketing

data class Expert(
    val id: Int,
    val name: String,
    val lastname: String,
    val email: String,
    val manager: Manager?,
    val expertise: Expertise
)


enum class Expertise {
    Software, Hardware, Network
}