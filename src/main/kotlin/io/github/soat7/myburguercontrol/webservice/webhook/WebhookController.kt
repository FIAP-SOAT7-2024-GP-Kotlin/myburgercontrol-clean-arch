package io.github.soat7.myburguercontrol.webservice.webhook

import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController("webhook-controller")
@RequestMapping(
    path = ["webhook"],
    produces = [MediaType.APPLICATION_JSON_VALUE],
)
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@SecurityRequirement(name = "Bearer Authentication")
class WebhookController {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        tags = ["99 - Adminstrativo"],
        summary = "Rota utilizada para receber atualizações de pagamento",
        description = "Rota utilizada para receber atualizações de pagamento",
    )
    fun receiveNotification(@RequestHeader headers: Map<String, String>, @RequestBody body: String): ResponseEntity<String> = run {
        logger.debug { "\nRecebeu notificação" }
        println("\nHeaders")
        headers.forEach { (key, value) -> logger.debug { "$key: $value" } }
        println("\nBody")
        logger.debug { body }
        ResponseEntity.ok("OK")
    }
}
