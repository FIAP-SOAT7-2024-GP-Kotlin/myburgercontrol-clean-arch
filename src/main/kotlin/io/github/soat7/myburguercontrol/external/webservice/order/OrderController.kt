package io.github.soat7.myburguercontrol.external.webservice.order

import io.github.soat7.myburguercontrol.adapters.controller.OrderHandler
import io.github.soat7.myburguercontrol.domain.entities.enum.OrderStatus
import io.github.soat7.myburguercontrol.external.webservice.common.PaginatedResponse
import io.github.soat7.myburguercontrol.external.webservice.order.api.OrderCreationRequest
import io.github.soat7.myburguercontrol.external.webservice.order.api.OrderResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController("order-controller")
@RequestMapping(
    path = ["orders"],
    produces = [MediaType.APPLICATION_JSON_VALUE],
)
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@SecurityRequirement(name = "Bearer Authentication")
class OrderController(
    private val orderHandler: OrderHandler,
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        tags = ["2 - Jornada do Pedido"],
        summary = "Utilize esta rota para criar um pedido",
        description = "Utilize esta rota para criar um pedido",
    )
    fun create(@RequestBody request: OrderCreationRequest): ResponseEntity<OrderResponse> =
        ResponseEntity.ok(orderHandler.create(request))

    @GetMapping
    @Operation(
        tags = ["2 - Jornada do Pedido"],
        summary = "Utilize esta rota para encontrar o(s) pedido(s) por cpf de cliente",
        description = "Utilize esta rota para encontrar o(s) pedido(s) por cpf de cliente",
    )
    fun findOrdersByCpf(@RequestParam("cpf") cpf: String): ResponseEntity<List<OrderResponse>> =
        ResponseEntity.ok(orderHandler.findOrdersByCustomerCpf(cpf))

    @GetMapping("/queue")
    @Operation(
        tags = ["2 - Jornada do Pedido"],
        summary = "Utilize esta rota para encontrar a fila de novos pedidos",
        description = "Utilize esta rota para encontrar a fila de novos pedidos",
    )
    fun findOrderQueue(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<PaginatedResponse<OrderResponse>> = ResponseEntity.ok(orderHandler.findOrderQueue(page, size))

    @GetMapping("/list")
    @Operation(
        tags = ["2 - Jornada do Pedido"],
        summary = "Utilize esta rota para encontrar a lista do(s) pedido(s)",
        description = "Utilize esta rota para encontrar a lista do(s) pedido(s)",
    )
    fun findAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<PaginatedResponse<OrderResponse>> = run {
        val pageable = PageRequest.of(page, size)
        val response = orderHandler.findAll(pageable)

        ResponseEntity.ok(
            PaginatedResponse(
                content = response.content.map { it },
                totalPages = response.totalPages,
                totalElements = response.totalElements,
                currentPage = response.number,
                pageSize = response.size,
            ),
        )
    }

    @PostMapping("/received", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        tags = ["2 - Jornada do Pedido"],
        summary = "Utilize esta rota para alterar o estado pedido para RECEBIDO",
        description = "Utilize esta rota para alterar o estado pedido para RECEBIDO",
    )
    fun updateOrderStatusToReceived(
        @RequestBody orderID: UUID,
    ) = ResponseEntity.ok(orderHandler.changeOrderStatus(OrderStatus.RECEIVED, orderID))

    @PostMapping("/in-progress", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        tags = ["2 - Jornada do Pedido"],
        summary = "Utilize esta rota para alterar o estado pedido para EM PREPARAÇÃO",
        description = "Utilize esta rota para alterar o estado pedido para EM PREPARAÇÃO",
    )
    fun updateOrderStatusToInProgress(
        @RequestBody orderID: UUID,
    ) = ResponseEntity.ok(orderHandler.changeOrderStatus(OrderStatus.IN_PROGRESS, orderID))

    @PostMapping("/ready", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        tags = ["2 - Jornada do Pedido"],
        summary = "Utilize esta rota para alterar o estado pedido para PRONTO",
        description = "Utilize esta rota para alterar o estado pedido para PRONTO",
    )
    fun updateOrderStatusToReady(
        @RequestBody orderID: UUID,
    ) = ResponseEntity.ok(orderHandler.changeOrderStatus(OrderStatus.READY, orderID))

    @PostMapping("/finished", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        tags = ["2 - Jornada do Pedido"],
        summary = "Utilize esta rota para alterar o estado pedido para FINALIZADO",
        description = "Utilize esta rota para alterar o estado pedido para FINALIZADO",
    )
    fun updateOrderStatusToFinished(
        @RequestBody orderID: UUID,
    ) = ResponseEntity.ok(orderHandler.changeOrderStatus(OrderStatus.FINISHED, orderID))
}
