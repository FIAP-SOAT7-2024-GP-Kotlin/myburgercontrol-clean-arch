package io.github.soat7.myburguercontrol.adapters.gateway

import io.github.soat7.myburguercontrol.domain.entities.Order
import io.github.soat7.myburguercontrol.external.thirdparty.api.PaymentResult

interface PaymentIntegrationRepository {

    fun requestPayment(order: Order): PaymentResult
}
