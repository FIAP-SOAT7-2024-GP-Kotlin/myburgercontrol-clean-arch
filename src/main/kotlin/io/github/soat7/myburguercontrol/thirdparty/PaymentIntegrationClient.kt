package io.github.soat7.myburguercontrol.thirdparty

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.soat7.myburguercontrol.business.exception.ReasonCode
import io.github.soat7.myburguercontrol.business.exception.ReasonCodeException
import io.github.soat7.myburguercontrol.business.mapper.toPaymentRequest
import io.github.soat7.myburguercontrol.business.model.Order
import io.github.soat7.myburguercontrol.business.repository.PaymentIntegrationRepository
import io.github.soat7.myburguercontrol.thirdparty.api.QRCodeData
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientResponseException
import org.springframework.web.client.RestTemplate

private val logger = KotlinLogging.logger {}

@Component
class PaymentIntegrationClient(
    @Value("\${third-party.payment-integration.url}") private val paymentServiceUrl: String,
    @Value("\${third-party.payment-integration.auth-token}") private val authToken: String,
    private val paymentRestTemplate: RestTemplate,
) : PaymentIntegrationRepository {

    override fun requestQRCodeDataForPayment(order: Order): QRCodeData {
        try {
            val headers = HttpHeaders().apply {
                contentType = MediaType.APPLICATION_JSON
                add("Authorization", authToken)
            }

            val orderToRequest = order.toPaymentRequest()

            logger.info { "Requesting PaymentData with [payload: $orderToRequest]" }

            val response = paymentRestTemplate.exchange(
                paymentServiceUrl,
                HttpMethod.POST,
                HttpEntity(orderToRequest, headers),
                QRCodeData::class.java,
            ).also { logger.info { "Received response ${it.body}" } }

            if (response.statusCode.is2xxSuccessful) {
                response.body?.let {
                    return it
                } ?: run {
                    throw ReasonCodeException(ReasonCode.PAYMENT_INTEGRATION_ERROR)
                }
            } else {
                throw ReasonCodeException(ReasonCode.UNEXPECTED_ERROR)
            }
        } catch (ex: RestClientResponseException) {
            logger.warn { "Integration error" }.also { throw ex }
        }
    }
}
