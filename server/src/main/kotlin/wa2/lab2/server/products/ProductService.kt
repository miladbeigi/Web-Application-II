package wa2.lab2.server.products

interface ProductService {
    fun getAllProducts(): List<ProductDTO>
    fun getProductById(ean: String): ProductDTO?
    fun createProduct(ean: String, name: String, brand: String): ProductDTO
}