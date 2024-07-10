package io.github.soat7.myburguercontrol.thirdparty.api

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigDecimal

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PaymentIntegrationRequest(
    val cashOut: CashOut,
    val description: String,
    val externalReference: String,
    val items: List<Item>,
    val notificationUrl: String = "",
    val sponsor: Sponsor = Sponsor(),
    val tittle: String = "",
    val totalAmount: BigDecimal,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CashOut(
    val amount: String,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class Item(
    val skuNumber: String,
    val category: String,
    val title: String,
    val description: String,
    val unitPrice: BigDecimal,
    val quantity: Int,
    val unitMeasure: String,
    val totalAmount: BigDecimal,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class Sponsor(
    val id: BigDecimal = BigDecimal(10),
)
