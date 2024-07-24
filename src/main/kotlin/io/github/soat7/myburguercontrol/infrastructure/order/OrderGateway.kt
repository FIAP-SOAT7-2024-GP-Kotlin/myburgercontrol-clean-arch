package io.github.soat7.myburguercontrol.infrastructure.order

import io.github.soat7.myburguercontrol.domain.mapper.toDomain
import io.github.soat7.myburguercontrol.domain.mapper.toPersistence
import io.github.soat7.myburguercontrol.domain.model.Order
import io.github.soat7.myburguercontrol.domain.repository.OrderRepository
import io.github.soat7.myburguercontrol.infrastructure.order.repository.OrderJpaRepository
import io.github.soat7.myburguercontrol.infrastructure.product.repository.ProductJpaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class OrderGateway(
    private val repository: OrderJpaRepository,
    private val productJpaRepository: ProductJpaRepository,
) : OrderRepository {

    override fun create(order: Order): Order = run {
        repository.save(
            order.toPersistence(order.customer.toPersistence(), order.payment?.toPersistence()) {
                productJpaRepository.findById(it).get()
            },
        ).toDomain()
    }

    override fun findByCustomerId(customerId: UUID): List<Order> =
        repository.findByCustomerId(customerId).map { it.toDomain() }

    override fun findNewOrders(status: String, pageable: Pageable): Page<Order> =
        repository.findAllByStatusOrderByCreatedAtAsc(status, pageable)
            .map { it.toDomain() }

    override fun update(order: Order): Order {
        return repository.save(
            order.toPersistence(order.customer.toPersistence(), order.payment?.toPersistence()) {
                productJpaRepository.findById(it).get()
            },
        ).toDomain()
    }

    override fun findById(orderId: UUID): Order? {
        return repository.findById(orderId).get().toDomain()
    }
}
