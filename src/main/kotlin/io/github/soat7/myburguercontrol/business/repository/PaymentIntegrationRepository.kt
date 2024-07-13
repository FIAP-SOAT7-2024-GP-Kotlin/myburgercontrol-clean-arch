package io.github.soat7.myburguercontrol.business.repository

import io.github.soat7.myburguercontrol.business.model.Order
import io.github.soat7.myburguercontrol.thirdparty.api.QRCodeData

interface PaymentIntegrationRepository {

    fun requestQRCodeDataForPayment(order: Order): QRCodeData
}
