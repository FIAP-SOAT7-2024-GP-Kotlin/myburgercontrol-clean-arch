package io.github.soat7.myburguercontrol.domain.usecase

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.soat7.myburguercontrol.exception.ReasonCode
import io.github.soat7.myburguercontrol.exception.ReasonCodeException
import io.github.soat7.myburguercontrol.webservice.notification.api.IpnEvent
import io.github.soat7.myburguercontrol.webservice.notification.api.MerchantOrderResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

private val logger: KLogger = KotlinLogging.logger {}

@Service
class NotificationIpnService(
    @Value("\${mercadopago.merchantURL}")
    private val baseURL: String,

    @Value("\${mercadopago.acessToken}")
    private val mpAccessToken: String,

    private val paymentUseCase: PaymentUseCase,
) {

    fun processIpn(headerEvent: Map<String, String>, bodyEvent: String): Boolean {
        logger.debug { "Process Webhook: $headerEvent $bodyEvent" }

        val objectMapper = ObjectMapper()
        val event = objectMapper.readValue(bodyEvent, IpnEvent::class.java)

        val parts = event.resource.split("/")
        val merchantId = parts.last()

        if (event.topic == "merchant_order") {
            try {
                val merchantOrderResponse = getIpnMerchantDetail(merchantId)
                println(merchantOrderResponse.toString())
                paymentUseCase.updatePayment(
                    merchantOrderResponse.externalReference.toString(),
                    merchantOrderResponse.orderStatus,
                )
            } catch (ex: Exception) {
                logger.error(ex) { "Error while checking event" }
                throw ReasonCodeException(ReasonCode.UNEXPECTED_ERROR, ex)
            }
            return true
        } else {
            return false
        }
    }

    private fun getIpnMerchantDetail(merchantId: String): MerchantOrderResponse {
        val url = "$baseURL/$merchantId"
        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        headers.setBearerAuth(mpAccessToken)

        val requestEntity = HttpEntity<Any>(headers)

        val response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            requestEntity,
            String::class.java,
        )

        val json = response.body.toString()
        val objectMapper = ObjectMapper()

        val merchantResponse = objectMapper.readValue(json, MerchantOrderResponse::class.java)

        return merchantResponse
    }
}
