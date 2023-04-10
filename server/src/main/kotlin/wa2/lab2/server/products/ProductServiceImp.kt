package wa2.lab2.server.products

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductServiceImp(private val productRepository: ProductRepository) : ProductService {
    override fun getAllProducts(): List<ProductDTO> {
        return productRepository.findAll().map { it.toDTO() }
    }

    override fun getProductById(ean: String): ProductDTO? {
        return productRepository.findByIdOrNull(ean)?.toDTO()
    }

    override fun addProduct(product: ProductDTO): ProductDTO {
        TODO("Not yet implemented")
    }

    override fun updateProduct(ean: String, product: ProductDTO): ProductDTO {
        TODO("Not yet implemented")
    }

    override fun deleteProduct(ean: String) {
        TODO("Not yet implemented")
    }

}