package io.github.soat7.myburguercontrol.external.webservice.user

import io.github.soat7.myburguercontrol.adapters.mapper.toDomain
import io.github.soat7.myburguercontrol.adapters.mapper.toResponse
import io.github.soat7.myburguercontrol.domain.usecase.UserUseCase
import io.github.soat7.myburguercontrol.external.webservice.user.api.UserCreationRequest
import io.github.soat7.myburguercontrol.external.webservice.user.api.UserResponse
import org.springframework.http.ResponseEntity
import java.util.UUID

class UserHandler(
    private val userUseCase: UserUseCase,
) {

    fun create(request: UserCreationRequest): ResponseEntity<UserResponse> {
        val user = userUseCase.create(request.toDomain())
        return ResponseEntity.ok(user.toResponse())
    }

    fun findUserById(id: UUID): ResponseEntity<UserResponse> {
        return userUseCase.findUserById(id)?.let {
            ResponseEntity.ok().body(it.toResponse())
        } ?: ResponseEntity.notFound().build()
    }

    fun findUserByCpf(cpf: String): ResponseEntity<UserResponse> {
        return userUseCase.findUserByCpf(cpf)?.let {
            ResponseEntity.ok().body(it.toResponse())
        } ?: ResponseEntity.notFound().build()
    }
}
