package io.github.soat7.myburguercontrol.domain.usecase

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.soat7.myburguercontrol.adapters.gateway.PaymentIntegrationRepository
import io.github.soat7.myburguercontrol.domain.entities.Order
import io.github.soat7.myburguercontrol.domain.entities.Payment
import io.github.soat7.myburguercontrol.domain.entities.enum.PaymentStatus
import io.github.soat7.myburguercontrol.exception.ReasonCode
import io.github.soat7.myburguercontrol.exception.ReasonCodeException
import io.github.soat7.myburguercontrol.external.db.payment.PaymentGateway

private val logger = KotlinLogging.logger {}

class PaymentUseCase(
    private val paymentIntegrationRepository: PaymentIntegrationRepository,
    private val paymentGateway: PaymentGateway,
) {

    fun startPaymentRequest(orderId: UUID): QRCodeData {
        val order = orderRepository.findById(orderId) ?: throw ReasonCodeException(ReasonCode.ORDER_NOT_FOUND)

        val payment = createPayment()
        val orderUpdated = order.copy(
            payment = payment,
        )
        orderRepository.update(orderUpdated)

        return paymentIntegrationRepository.requestQRCodeDataForPayment(orderUpdated)
    }

    private fun createPayment(): Payment {
        logger.info { "Creating payment" }

        return paymentGateway.create(Payment())
    }

    fun updatePayment(paymentId: String, paymentStatus: String): Payment {
        logger.info { "Update payment: $paymentId status: $paymentStatus" }

        val payment = paymentId.let {
            paymentGateway.findById(UUID.fromString(paymentId))
        } ?: throw ReasonCodeException(ReasonCode.PAYMENT_NOT_FOUND)

        val updatedPayment = payment.copy(
            status = PaymentStatus.fromString(paymentStatus),
        )
        paymentGateway.update(updatedPayment)

        logger.info { "Successfully update with status return: [${updatedPayment.status.name}]" }

        if (updatedPayment.status == PaymentStatus.DENIED) throw ReasonCodeException(ReasonCode.PAYMENT_INTEGRATION_ERROR)

        return updatedPayment
    }
}
