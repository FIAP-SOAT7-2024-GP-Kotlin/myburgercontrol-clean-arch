package io.github.soat7.myburguercontrol.external.thirdparty.api

data class PaymentResult(
    val authorizationId: String?,
    val approved: Boolean,
)
