package io.github.soat7.myburguercontrol.domain.repository

import io.github.soat7.myburguercontrol.domain.model.Customer
import java.util.UUID

interface CustomerRepository {

    fun create(customer: Customer): Customer

    fun findCustomerById(id: UUID): Customer?

    fun findCustomerByCpf(cpf: String): Customer?
}
