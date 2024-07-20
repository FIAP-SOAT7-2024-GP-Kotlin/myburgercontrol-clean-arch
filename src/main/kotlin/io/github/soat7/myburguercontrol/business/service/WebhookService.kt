package io.github.soat7.myburguercontrol.business.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.soat7.myburguercontrol.business.enum.PaymentStatus
import io.github.soat7.myburguercontrol.business.exception.ReasonCode
import io.github.soat7.myburguercontrol.business.exception.ReasonCodeException
import io.github.soat7.myburguercontrol.webservice.webhook.api.MercadoPagoPaymentResponse
import io.github.soat7.myburguercontrol.webservice.webhook.api.WebhookEvent
import org.apache.commons.codec.digest.HmacUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

private val logger: KLogger = KotlinLogging.logger {}

@Service
class WebhookService(
    @Value("\${mercadopago.paymentURL}")
    private val baseURL: String,

    @Value("\${mercadopago.webhookKey}")
    private val wbSecretKey: String,

    @Value("\${mercadopago.acessToken")
    private val mpAccessToken: String,

    private val paymentService: PaymentService,
) {

    fun processEvent(headerEvent: Map<String, String>, bodyEvent: WebhookEvent): Boolean {
        val validatedEvent: Boolean?

        try {
            logger.debug { "Check Event: $headerEvent $bodyEvent" }
            validatedEvent = validateEvent(headerEvent, bodyEvent)
        } catch (ex: Exception) {
            logger.error(ex) { "Error while checking event" }
            throw ReasonCodeException(ReasonCode.UNEXPECTED_ERROR, ex)
        }

        if (!validatedEvent) {
            return false
        }

        if (bodyEvent.action == "payment.updated") {
            val mercadoPagoPayment = getPaymentDetail(bodyEvent.data.id)
            paymentService.updatePayment(
                mercadoPagoPayment.externalReference.toString(),
                PaymentStatus.valueOf(mercadoPagoPayment.status),
            )
            return true
        } else {
            return false
        }
    }

    private fun validateEvent(header: Map<String, String>, body: WebhookEvent): Boolean {
        val xRequestId = header["x-request-id"]
        val xSignature = header["x-signature"]
        val xDataID = body.data.id

        val partsSignature = xSignature?.split(",")
        val signatureValues = mutableMapOf<String, String>()
        partsSignature?.forEach { part ->
            val (key, value) = part.split("=")
            signatureValues[key] = value
        }

        val ts = signatureValues["ts"]
        val v1 = signatureValues["v1"]
        val signedTemplate = "id:$xDataID;request-id:$xRequestId;ts:$ts;"
        val cyphedSignature: String = HmacUtils("HmacSHA256", wbSecretKey).hmacHex(signedTemplate)

        return cyphedSignature == v1
    }

    private fun getPaymentDetail(paymentId: String): MercadoPagoPaymentResponse {
        val url = "$baseURL/$paymentId"
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

        val paymentResponse = objectMapper.readValue(json, MercadoPagoPaymentResponse::class.java)

        return paymentResponse
    }
}