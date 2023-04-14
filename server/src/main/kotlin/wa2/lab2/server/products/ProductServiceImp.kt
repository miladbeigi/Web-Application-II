package wa2.lab2.server.products

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import wa2.lab2.server.products.exceptions.ProductNotFoundException

@Service
class ProductServiceImp(private val productRepository: ProductRepository) : ProductService {
    override fun getAllProducts(): List<ProductDTO> {
        val products : List<Product> = productRepository.findAll()
        if (products.isEmpty()) {
            throw ProductNotFoundException("No products found")
        } else {
        return products.map { it.toDTO() }
        }
    }

    override fun getProductById(ean: String): ProductDTO? {
        val product : ProductDTO? = productRepository.findByIdOrNull(ean)?.toDTO()
        if (product == null) {
            throw ProductNotFoundException("Product with ean $ean not found")
        } else {
            return product
        }
    }
}