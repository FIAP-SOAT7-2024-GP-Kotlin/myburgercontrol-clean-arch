package io.github.soat7.myburguercontrol.thirdparty.api

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigDecimal

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PaymentIntegrationRequest(
    val description: String,
    val externalReference: String,
    val items: List<Item>,
    val notificationUrl: String = "https://concise-humble-mosquito.ngrok-free.app/api/v1/webhook",
    val totalAmount: BigDecimal,
    val title: String = "Oder",
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class Item(
    val title: String,
    val description: String,
    val unitPrice: BigDecimal,
    val quantity: Int,
    val unitMeasure: String,
    val totalAmount: BigDecimal,
)
