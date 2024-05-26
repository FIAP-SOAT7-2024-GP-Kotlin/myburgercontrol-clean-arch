package io.github.soat7.myburguercontrol.domain.service

import io.github.soat7.myburguercontrol.application.ports.inbound.PaymentServicePort
import io.github.soat7.myburguercontrol.application.ports.outbound.PaymentDatabasePort
import io.github.soat7.myburguercontrol.application.ports.outbound.PaymentIntegrationPort
import io.github.soat7.myburguercontrol.domain.enum.PaymentStatus
import io.github.soat7.myburguercontrol.domain.model.Order
import io.github.soat7.myburguercontrol.domain.model.Payment
import mu.KLogging

class PaymentService(
    private val paymentIntegrationPort: PaymentIntegrationPort,
    private val paymentDatabasePort: PaymentDatabasePort
) : PaymentServicePort {

    private companion object : KLogging()

    override fun requestPayment(order: Order): Payment {
        logger.info { "Starting to request payment integration for order id: [${order.id}]" }

        val paymentResult = paymentIntegrationPort.requestPayment(order)

        order.payment.apply {
            authorizationId = paymentResult.authorizationId
            status = checkApproval(paymentResult.approved)
        }

        paymentDatabasePort.create(order.payment)

        logger.info { "Successfully integrated with status return: [${order.payment.status.name}]" }

        return order.payment
    }

    private fun checkApproval(approved: Boolean): PaymentStatus =
        if (approved) PaymentStatus.APPROVED else PaymentStatus.DENIED
}
