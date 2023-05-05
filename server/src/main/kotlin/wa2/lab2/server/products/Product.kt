package wa2.lab2.server.products

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "products")
data class Product (
    @Id
    var ean: String = "",
    var name: String = "",
    var brand: String = ""
)