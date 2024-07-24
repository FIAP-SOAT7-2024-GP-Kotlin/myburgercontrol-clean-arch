package io.github.soat7.myburguercontrol.domain.usecase

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.soat7.myburguercontrol.domain.enum.OrderStatus
import io.github.soat7.myburguercontrol.domain.exception.ReasonCode
import io.github.soat7.myburguercontrol.domain.exception.ReasonCodeException
import io.github.soat7.myburguercontrol.domain.model.Customer
import io.github.soat7.myburguercontrol.domain.model.Order
import io.github.soat7.myburguercontrol.domain.model.OrderDetail
import io.github.soat7.myburguercontrol.domain.model.OrderItem
import io.github.soat7.myburguercontrol.infrastructure.order.OrderGateway
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID

private val logger = KotlinLogging.logger {}

class OrderUseCase(
    private val orderGateway: OrderGateway,
    private val customerUseCase: CustomerUseCase,
    private val productUseCase: ProductUseCase,
    private val paymentUseCase: PaymentUseCase,
) {

    fun createOrder(orderDetail: OrderDetail): Order {
        val customer = customerUseCase.findCustomerByCpf(orderDetail.customerCpf)
            ?: throw ReasonCodeException(ReasonCode.CUSTOMER_NOT_FOUND)

        val items = buildOrderItems(orderDetail)

        val order = setupOrder(customer, items)

        paymentUseCase.requestPayment(order)

        return orderGateway.update(order.copy(status = OrderStatus.RECEIVED))
    }

    fun findOrdersByCustomerCpf(cpf: String): List<Order> {
        logger.info { "Order.findOrders(cpf = $cpf)" }
        val customer = customerUseCase.findCustomerByCpf(cpf)
            ?: throw ReasonCodeException(ReasonCode.CUSTOMER_NOT_FOUND)

        return orderGateway.findByCustomerId(customer.id)
    }

    fun findQueuedOrders(pageable: Pageable): Page<Order> {
        logger.info { "Finding orders with status: [${OrderStatus.NEW}]" }
        return orderGateway.findNewOrders(OrderStatus.NEW.name, pageable)
    }

    fun changeOrderStatus(status: OrderStatus, orderId: UUID): Order {
        return orderGateway.update(
            orderGateway.findById(orderId)?.copy(status = status)
                ?: throw ReasonCodeException(ReasonCode.ORDER_NOT_FOUND),
        )
    }

    private fun buildOrderItems(orderDetail: OrderDetail): List<OrderItem> {
        val items = orderDetail.items.map { item ->
            productUseCase.findById(item.productId)?.let { product ->
                OrderItem(
                    id = UUID.randomUUID(),
                    product = product,
                    quantity = item.quantity,
                    comment = item.comment,
                )
            } ?: throw ReasonCodeException(ReasonCode.INVALID_PRODUCT)
        }
        return items
    }

    private fun setupOrder(
        customer: Customer,
        items: List<OrderItem>,
    ): Order {
        val order = orderGateway.create(
            Order(
                id = UUID.randomUUID(),
                customer = customer,
                items = items,
            ),
        )

        val payment = paymentUseCase.createPayment()

        return orderGateway.update(order.copy(payment = payment))
    }
}
