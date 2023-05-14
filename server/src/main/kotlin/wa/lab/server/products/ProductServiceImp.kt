package wa.lab.server.products

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import wa.lab.server.products.exceptions.ProductNotFoundException

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

    override fun createProduct(ean: String, name: String, brand: String): ProductDTO {
        if (productRepository.existsById(ean)) {
            throw ProductNotFoundException("Product with ean $ean already exists")
        }
        val savedProduct = productRepository.save(Product(ean, name, brand))
        println("Product created: ${savedProduct.ean}")
        return savedProduct.toDTO()
    }
}