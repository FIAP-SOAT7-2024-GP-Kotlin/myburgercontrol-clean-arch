package io.github.soat7.myburguercontrol.external.webservice.order

import io.github.soat7.myburguercontrol.adapters.mapper.toOrderDetails
import io.github.soat7.myburguercontrol.adapters.mapper.toResponse
import io.github.soat7.myburguercontrol.domain.entities.enum.OrderStatus
import io.github.soat7.myburguercontrol.domain.usecase.OrderUseCase
import io.github.soat7.myburguercontrol.external.webservice.common.PaginatedResponse
import io.github.soat7.myburguercontrol.external.webservice.order.api.request.OrderCreationRequest
import io.github.soat7.myburguercontrol.external.webservice.order.api.response.OrderResponse
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import java.util.UUID

class OrderHandler(
    private val orderUseCase: OrderUseCase,
) {

    fun create(request: OrderCreationRequest): ResponseEntity<OrderResponse> {
        val response = orderUseCase.createOrder(request.toOrderDetails())
        return ResponseEntity.ok(response.toResponse())
    }

    fun findOrdersByCustomerCpf(cpf: String): ResponseEntity<List<OrderResponse>> {
        return ResponseEntity.ok(orderUseCase.findOrdersByCustomerCpf(cpf).map { it.toResponse() })
    }

    fun findOrderQueue(page: Int, size: Int): ResponseEntity<PaginatedResponse<OrderResponse>> {
        val pageable = PageRequest.of(page, size)
        val response = orderUseCase.findQueuedOrders(pageable)

        return ResponseEntity.ok(
            PaginatedResponse(
                content = response.content.map { it.toResponse() },
                totalPages = response.totalPages,
                totalElements = response.totalElements,
                currentPage = response.number,
                pageSize = response.size,
            ),
        )
    }

    fun changeOrderStatus(status: OrderStatus, orderId: UUID): ResponseEntity<OrderResponse> {
        val response = orderUseCase.changeOrderStatus(status, orderId)
        return ResponseEntity.ok(response.toResponse())
    }
}
