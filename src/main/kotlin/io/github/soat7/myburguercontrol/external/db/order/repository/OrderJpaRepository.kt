package io.github.soat7.myburguercontrol.external.db.order.repository

import io.github.soat7.myburguercontrol.external.db.order.entity.OrderEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface OrderJpaRepository : JpaRepository<OrderEntity, UUID> {

    fun findByCustomerId(customerId: UUID): List<OrderEntity>

    fun findAllByStatusOrderByCreatedAtAsc(status: String, pageable: Pageable): Page<OrderEntity>
}
