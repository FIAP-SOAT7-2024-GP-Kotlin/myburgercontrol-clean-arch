package io.github.soat7.myburguercontrol.adapters.controller

import io.github.soat7.myburguercontrol.domain.usecase.PaymentUseCase
import io.github.soat7.myburguercontrol.external.thirdparty.api.QRCodeData
import java.util.UUID

class PaymentHandler(
    private val paymentUseCase: PaymentUseCase,
) {
    fun startPaymentRequest(orderIdentification: UUID): QRCodeData {
        return paymentUseCase.startPaymentRequest(orderIdentification)
    }
}
