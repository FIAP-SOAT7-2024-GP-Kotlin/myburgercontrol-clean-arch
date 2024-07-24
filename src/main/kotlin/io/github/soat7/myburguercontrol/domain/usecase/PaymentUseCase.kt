package io.github.soat7.myburguercontrol.domain.usecase

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.soat7.myburguercontrol.domain.enum.PaymentStatus
import io.github.soat7.myburguercontrol.domain.exception.ReasonCode
import io.github.soat7.myburguercontrol.domain.exception.ReasonCodeException
import io.github.soat7.myburguercontrol.domain.model.Order
import io.github.soat7.myburguercontrol.domain.model.Payment
import io.github.soat7.myburguercontrol.domain.repository.PaymentIntegrationRepository
import io.github.soat7.myburguercontrol.infrastructure.payment.PaymentGateway

private val logger = KotlinLogging.logger {}

class PaymentUseCase(
    private val paymentIntegrationRepository: PaymentIntegrationRepository,
    private val paymentGateway: PaymentGateway,
) {

    fun createPayment(): Payment {
        logger.info { "Creating payment" }

        return paymentGateway.create(Payment())
    }

    fun requestPayment(order: Order): Payment {
        logger.info { "Starting to request payment integration for order id: [${order.id}]" }

        val payment = order.payment?.let {
            paymentGateway.findById(it.id)
        } ?: throw ReasonCodeException(ReasonCode.PAYMENT_NOT_FOUND)

        val paymentResult = paymentIntegrationRepository.requestPayment(order)

        val updatedPayment = payment.copy(
            status = checkApproval(paymentResult.approved),
            authorizationId = paymentResult.authorizationId,
        )
        paymentGateway.update(updatedPayment)

        logger.info { "Successfully integrated with status return: [${updatedPayment.status.name}]" }

        if (updatedPayment.status == PaymentStatus.DENIED) throw ReasonCodeException(ReasonCode.PAYMENT_INTEGRATION_ERROR)

        return updatedPayment
    }

    private fun checkApproval(approved: Boolean): PaymentStatus =
        if (approved) PaymentStatus.APPROVED else PaymentStatus.DENIED
}