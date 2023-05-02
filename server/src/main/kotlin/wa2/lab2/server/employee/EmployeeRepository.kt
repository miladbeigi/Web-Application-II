package wa2.lab2.server.employee

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ExpertRepository : JpaRepository<Expert, Long> {
}

@Repository
interface ManagerRepository : JpaRepository<Manager, Long> {
}