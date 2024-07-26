package io.github.soat7.myburguercontrol.adapters.gateway

import io.github.soat7.myburguercontrol.domain.entities.User
import java.util.UUID

interface UserRepository {

    fun create(user: User): User

    fun findUserById(id: UUID): User?

    fun findUserByCpf(cpf: String): User?

    fun findByAll(): List<User>

    fun deleteByUUID(uuid: UUID): Boolean
}
