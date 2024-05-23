package io.github.soat7.myburguercontrol.domain.exception

import org.springframework.http.HttpStatus

enum class ReasonCode(
    val status: HttpStatus,
    val code: String,
    val description: String
) {
    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "01", "An unexpected error occurred"),

    CPF_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "10", "The given document is already registered"),
    CUSTOMER_NOT_FOUND(HttpStatus.BAD_REQUEST, "11", "Unable to create an order due to customer not found"),
    INVALID_PRODUCT(HttpStatus.UNPROCESSABLE_ENTITY, "12", "Error while creating order due to invalid product")

    PAYMENT_INTEGRATION_ERROR(HttpStatus.UNPROCESSABLE_ENTITY, "20", "The payment provider did not return the required values")
}
