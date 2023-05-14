package wa.lab.server.products

import jakarta.validation.constraints.NotNull

data class ProductDTO (
    @field: NotNull(message = "EAN is required")
    val ean: String,
    @field: NotNull(message = "Name is required")
    val name: String,
    @field: NotNull(message = "Brand is required")
    val brand: String)

fun Product.toDTO(): ProductDTO {
    return ProductDTO(ean, name, brand)
}

fun ProductDTO.toEntity(): Product {
    return Product(ean, name, brand)
}