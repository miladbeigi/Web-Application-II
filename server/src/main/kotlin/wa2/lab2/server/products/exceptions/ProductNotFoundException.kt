package wa2.lab2.server.products.exceptions

class ProductNotFoundException(override val message: String) : RuntimeException(message) {

}
