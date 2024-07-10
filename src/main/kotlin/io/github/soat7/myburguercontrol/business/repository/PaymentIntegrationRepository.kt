package io.github.soat7.myburguercontrol.business.repository

import io.github.soat7.myburguercontrol.business.model.Order
import io.github.soat7.myburguercontrol.thirdparty.api.PaymentResult
import io.github.soat7.myburguercontrol.thirdparty.api.QRCodeData

interface PaymentIntegrationRepository {

    fun requestPayment(order: Order): PaymentResult

    fun requestQRCodeDataForPayment(order: Order): QRCodeData
}
