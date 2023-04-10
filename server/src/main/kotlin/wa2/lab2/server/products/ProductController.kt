package wa2.lab2.server.products

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController (
    private val productService: ProductService
) {
    @GetMapping("/products")
    fun getAllProducts(): List<ProductDTO> { return productService.getAllProducts()}
    @GetMapping("/products/{ean}")
    fun getProductById(@PathVariable ean: String ): ProductDTO? { return productService.getProductById(ean) }
}