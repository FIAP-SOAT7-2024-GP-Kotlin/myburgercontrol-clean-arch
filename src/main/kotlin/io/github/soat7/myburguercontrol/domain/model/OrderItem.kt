package io.github.soat7.myburguercontrol.domain.model

import java.util.UUID

data class OrderItem(
    val id: UUID,
    val product: Product,
    val quantity: Int,
    val comment: String? = null,
)
