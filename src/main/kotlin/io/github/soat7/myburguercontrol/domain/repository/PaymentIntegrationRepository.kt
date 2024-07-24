package io.github.soat7.myburguercontrol.domain.repository

import io.github.soat7.myburguercontrol.domain.model.Order
import io.github.soat7.myburguercontrol.thirdparty.api.PaymentResult

interface PaymentIntegrationRepository {

    fun requestPayment(order: Order): PaymentResult
}
