package io.github.soat7.myburguercontrol.external.webservice.order.api

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import java.util.UUID

data class OrderCreationRequest(
    @NotBlank
    val customerCpf: String,
    @NotEmpty
    val items: List<OrderItem>,
) {
    data class OrderItem(
        val productId: UUID,
        val quantity: Int,
        val comment: String? = null,
    )
}
