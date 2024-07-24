package io.github.soat7.myburguercontrol.webservice.customer

import io.github.soat7.myburguercontrol.domain.mapper.toDomain
import io.github.soat7.myburguercontrol.domain.mapper.toResponse
import io.github.soat7.myburguercontrol.domain.usecase.CustomerUseCase
import io.github.soat7.myburguercontrol.webservice.customer.api.request.CustomerCreationRequest
import io.github.soat7.myburguercontrol.webservice.customer.api.response.CustomerResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CustomerController(
    private val customerUseCase: CustomerUseCase
) {

    fun createCustomer(request: CustomerCreationRequest): ResponseEntity<CustomerResponse> {
        val customer = customerUseCase.create(request.toDomain())
        return ResponseEntity.ok(customer.toResponse())
    }

    fun findCustomerById(id: UUID): ResponseEntity<CustomerResponse> {
        return customerUseCase.findCustomerById(id)?.let {
            ResponseEntity.ok().body(it.toResponse())
        } ?: ResponseEntity.notFound().build()
    }

    fun findCustomerByCpf(cpf: String): ResponseEntity<CustomerResponse> {
        return customerUseCase.findCustomerByCpf(cpf)?.let {
            ResponseEntity.ok().body(it.toResponse())
        } ?: ResponseEntity.notFound().build()
    }
}
