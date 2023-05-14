package wa.lab.server.employee

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ExpertRepository : JpaRepository<wa.lab.server.employee.Expert, Long> {
    @Query("SELECT m FROM Expert m WHERE m.email = :email")
    fun findByEmail(email: String): List<wa.lab.server.employee.Expert>
}

@Repository
interface ManagerRepository : JpaRepository<wa.lab.server.employee.Manager, Long> {

    @Query("SELECT m FROM Manager m WHERE m.email = :email")
    fun findByEmail(email: String): List<wa.lab.server.employee.Manager>
}