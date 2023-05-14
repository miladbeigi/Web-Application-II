package wa.lab.server.products

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController (
    private val productService: ProductService
) {
    @GetMapping("/api/products")
    fun getAllProducts(): List<ProductDTO> { return productService.getAllProducts()}
    @GetMapping("/api/products/{ean}")
    fun getProductById(@PathVariable ean: String ): ProductDTO? { return productService.getProductById(ean) }
}