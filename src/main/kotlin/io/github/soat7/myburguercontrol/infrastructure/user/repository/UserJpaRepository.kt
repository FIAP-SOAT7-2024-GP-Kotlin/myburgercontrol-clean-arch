package io.github.soat7.myburguercontrol.infrastructure.user.repository

import io.github.soat7.myburguercontrol.infrastructure.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserJpaRepository : JpaRepository<UserEntity, UUID> {

    fun findByCpf(email: String): UserEntity?
}
