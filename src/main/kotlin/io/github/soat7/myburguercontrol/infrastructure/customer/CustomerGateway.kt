package io.github.soat7.myburguercontrol.infrastructure.customer

import io.github.soat7.myburguercontrol.domain.mapper.toDomain
import io.github.soat7.myburguercontrol.domain.mapper.toPersistence
import io.github.soat7.myburguercontrol.domain.model.Customer
import io.github.soat7.myburguercontrol.domain.repository.CustomerRepository
import io.github.soat7.myburguercontrol.infrastructure.customer.repository.CustomerJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CustomerGateway(
    private val repository: CustomerJpaRepository,
) : CustomerRepository {

    override fun create(customer: Customer): Customer = repository.save(customer.toPersistence()).toDomain()

    override fun findCustomerByCpf(cpf: String): Customer? = repository.findByCpf(cpf)?.toDomain()

    override fun findCustomerById(id: UUID): Customer? = repository.findByIdOrNull(id)?.toDomain()
}
