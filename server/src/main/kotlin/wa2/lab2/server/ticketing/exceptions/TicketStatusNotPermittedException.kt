package wa2.lab2.server.ticketing.exceptions

class TicketStatusNotPermittedException(override val message: String) : RuntimeException(message) {
}