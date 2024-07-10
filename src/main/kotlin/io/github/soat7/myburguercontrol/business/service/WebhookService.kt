package io.github.soat7.myburguercontrol.business.service

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.soat7.myburguercontrol.business.exception.ReasonCode
import io.github.soat7.myburguercontrol.business.exception.ReasonCodeException
import io.github.soat7.myburguercontrol.business.repository.PaymentRepository
import io.github.soat7.myburguercontrol.webservice.webhook.api.WebhookEvent
import org.apache.commons.codec.digest.HmacUtils
import org.springframework.stereotype.Service

private val logger: KLogger = KotlinLogging.logger {}

@Service
class WebhookService(
    private val databasePort: PaymentRepository,
) {

    fun processEvent(header: Map<String, String>, body: WebhookEvent): Boolean {
        val eventChecked: Boolean?

        try {
            logger.debug { "Check Event: $header $body" }
            eventChecked = checkEvent(header, body)
        } catch (ex: Exception) {
            logger.error(ex) { "Error while checking event" }
            throw ReasonCodeException(ReasonCode.UNEXPECTED_ERROR, ex)
        }

        /* Pendente
        *   - buscar na URL: https://api.mercadopago.com/v1/payments/[ID] detalhes referente a atualizacão do pagamento
        *   - atualizar em nossa base o Payment
        */
        return eventChecked && body.action == "payment.updated"
    }

    fun checkEvent(header: Map<String, String>, body: WebhookEvent): Boolean {
        val secretKey = "dc06864caafd611b67646b59cf1b3238e0a44010c1173ddf1c3fa61eb0a4c0e7"
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
        val cyphedSignature: String = HmacUtils("HmacSHA256", secretKey).hmacHex(signedTemplate)

        return cyphedSignature == v1
    }
}
