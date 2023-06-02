package wa.lab.server.products

import io.micrometer.observation.annotation.Observed
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@Observed
class ProductController (
    private val productService: ProductService
) {
    @GetMapping("/api/products")
    fun getAllProducts(): List<ProductDTO> { return productService.getAllProducts()}
    @GetMapping("/api/products/{ean}")
    fun getProductById(@PathVariable ean: String ): ProductDTO? { return productService.getProductById(ean) }
}