package io.github.soat7.myburguercontrol.infrastructure.customer.repository

import io.github.soat7.myburguercontrol.infrastructure.customer.entity.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CustomerJpaRepository : JpaRepository<CustomerEntity, UUID> {

    fun findByCpf(cpf: String): CustomerEntity?
}
