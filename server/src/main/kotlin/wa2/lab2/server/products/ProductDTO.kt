package wa2.lab2.server.products

data class ProductDTO (
    val ean: String,
    val name: String,
    val brand: String)

fun Product.toDTO(): ProductDTO {
    return ProductDTO(ean, name, brand)
}
