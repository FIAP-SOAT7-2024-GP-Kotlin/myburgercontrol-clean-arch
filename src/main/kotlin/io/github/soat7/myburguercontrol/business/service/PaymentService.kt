package io.github.soat7.myburguercontrol.business.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.soat7.myburguercontrol.business.exception.ReasonCode
import io.github.soat7.myburguercontrol.business.exception.ReasonCodeException
import io.github.soat7.myburguercontrol.business.model.Payment
import io.github.soat7.myburguercontrol.business.repository.OrderRepository
import io.github.soat7.myburguercontrol.business.repository.PaymentIntegrationRepository
import io.github.soat7.myburguercontrol.business.repository.PaymentRepository
import io.github.soat7.myburguercontrol.thirdparty.api.QRCodeData
import org.springframework.stereotype.Service
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Service
class PaymentService(
    private val paymentIntegrationRepository: PaymentIntegrationRepository,
    private val paymentRepository: PaymentRepository,
    private val orderRepository: OrderRepository,
) {

    fun startPaymentRequest(orderId: UUID): QRCodeData {
        val order = orderRepository.findById(orderId) ?: throw ReasonCodeException(ReasonCode.ORDER_NOT_FOUND)

        createPayment()

        return paymentIntegrationRepository.requestQRCodeDataForPayment(order)
    }

    private fun createPayment(): Payment {
        logger.info { "Creating payment" }

        return paymentRepository.create(Payment())
    }
}
