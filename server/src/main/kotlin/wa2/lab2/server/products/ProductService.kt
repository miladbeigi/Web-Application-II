package wa2.lab2.server.products

interface ProductService {
    fun getAllProducts(): List<ProductDTO>
    fun getProductById(ean: String): ProductDTO?
    fun addProduct(product: ProductDTO): ProductDTO
    fun updateProduct(ean: String, product: ProductDTO): ProductDTO
    fun deleteProduct(ean: String)
}