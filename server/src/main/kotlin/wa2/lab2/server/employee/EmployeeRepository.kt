package wa2.lab2.server.employee

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ExpertRepository : JpaRepository<Expert, Long> {
    @Query("SELECT m FROM Expert m WHERE m.email = :email")
    fun findByEmail(email: String): List<Expert>
}

@Repository
interface ManagerRepository : JpaRepository<Manager, Long> {

    @Query("SELECT m FROM Manager m WHERE m.email = :email")
    fun findByEmail(email: String): List<Manager>
}