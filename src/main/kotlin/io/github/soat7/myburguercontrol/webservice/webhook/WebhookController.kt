package io.github.soat7.myburguercontrol.webservice.webhook

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.soat7.myburguercontrol.business.service.WebhookService
import io.github.soat7.myburguercontrol.webservice.webhook.api.WebhookEvent
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController("webhook-controller")
@RequestMapping(
    path = ["webhook"],
    produces = [MediaType.APPLICATION_JSON_VALUE],
)
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@SecurityRequirement(name = "Bearer Authentication")
class WebhookController(
    private val webhookService: WebhookService,
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        tags = ["99 - Adminstrativo"],
        summary = "Rota utilizada para receber atualizações de pagamento",
        description = "Rota utilizada para receber atualizações de pagamento",
    )
    fun notificationPaymentUpdated(
        @RequestHeader header: Map<String, String>,
        @RequestBody body: WebhookEvent,
    ): ResponseEntity<String> = run {
        logger.debug { "\nRecebeu notificação Updated\nHeader:\n$header\nBody:\n$body" }
        webhookService.processEvent(header, body)
        ResponseEntity.ok("OK")
    }

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        params = ["id", "topic"],
    )
    @Operation(
        tags = ["99 - Adminstrativo"],
        summary = "Rota utilizada para receber a notificação de pagamento criado",
        description = "Rota utilizada para receber atualizações de pagamento",
    )
    fun notificationPaymentCreated(
        @RequestParam("id") id: String,
        @RequestParam("topic") topic: String,
        @RequestHeader header: Map<String, String>,
        @RequestBody body: String,
    ): ResponseEntity<String> = run {
        logger.debug {
            "\nRecebeu notificação Created" +
                "\ndata.id=$id\ntype=$topic" +
                "\nHeader:\n$header\nBody:\n$body"
        }
        ResponseEntity.ok("OK")
    }
}
