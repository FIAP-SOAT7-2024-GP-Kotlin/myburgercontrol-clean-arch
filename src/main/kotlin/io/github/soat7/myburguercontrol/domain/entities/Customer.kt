package io.github.soat7.myburguercontrol.domain.entities

import java.util.UUID

data class Customer(
    val id: UUID,
    val cpf: String,
    val name: String? = null,
    val email: String? = null,
)
