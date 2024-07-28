package io.github.soat7.myburguercontrol.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("mercadopago")
data class MercadoPagoProperties(
    val paymentURL: String,
    val wbSecretKey: String,
    val accessToken: String,
)
