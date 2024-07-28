package io.github.soat7.myburguercontrol.external.webservice.payment

import io.github.soat7.myburguercontrol.adapters.controller.PaymentHandler
import io.github.soat7.myburguercontrol.external.thirdparty.api.QRCodeData
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController("payment-controller")
@RequestMapping(
    path = ["payment"],
    produces = [MediaType.APPLICATION_JSON_VALUE],
)
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@SecurityRequirement(name = "Bearer Authentication")
class PaymentController(
    private val paymentHandler: PaymentHandler,
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        tags = ["Jornada de Pagamento"],
        summary = "Utilize esta rota iniciar uma solicitação de QRCode para pagamento",
        description = "Essa rota inicia a integração com o MercadoPago, solicitando os dados para a criação de um QRCode de pagamento",
    )
    fun startPayment(@RequestBody orderID: UUID): ResponseEntity<QRCodeData>? = run {
        return ResponseEntity.ok(paymentHandler.startPaymentRequest(orderID))
    }
}
