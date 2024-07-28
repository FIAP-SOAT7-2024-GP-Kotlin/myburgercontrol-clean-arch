package io.github.soat7.myburguercontrol.adapters.controller

import io.github.soat7.myburguercontrol.domain.usecase.PaymentUseCase
import io.github.soat7.myburguercontrol.external.thirdparty.api.QRCodeData
import io.github.soat7.myburguercontrol.webservice.payment.api.OrderIdentification

class PaymentHandler(
    private val paymentUseCase: PaymentUseCase
) {
    fun startPaymentRequest(orderIdentification: OrderIdentification) : QRCodeData {
        return paymentUseCase.startPaymentRequest(orderIdentification.id)
    }
}
