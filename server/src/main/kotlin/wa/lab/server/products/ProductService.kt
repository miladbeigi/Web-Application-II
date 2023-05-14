package wa.lab.server.products

interface ProductService {
    fun getAllProducts(): List<ProductDTO>
    fun getProductById(ean: String): ProductDTO?
    fun createProduct(ean: String, name: String, brand: String): ProductDTO
}