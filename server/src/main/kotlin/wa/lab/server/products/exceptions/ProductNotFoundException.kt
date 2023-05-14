package wa.lab.server.products.exceptions

class ProductNotFoundException(override val message: String) : RuntimeException(message) {

}
