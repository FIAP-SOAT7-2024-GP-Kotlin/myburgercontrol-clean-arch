package io.github.soat7.myburguercontrol.domain.mapper

import io.github.soat7.myburguercontrol.domain.enum.PaymentStatus
import io.github.soat7.myburguercontrol.domain.model.Payment
import io.github.soat7.myburguercontrol.infrastructure.payment.entity.PaymentEntity
import io.github.soat7.myburguercontrol.thirdparty.api.PaymentIntegrationResponse
import io.github.soat7.myburguercontrol.thirdparty.api.PaymentResult

fun PaymentIntegrationResponse.toDto(approved: Boolean) =
    PaymentResult(authorizationId = this.authorizationId, approved)

fun Payment.toPersistence() = PaymentEntity(
    id = this.id,
    authorizationId = this.authorizationId,
    status = this.status.toString(),
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
)

fun PaymentEntity.toDomain() = Payment(
    id = this.id,
    authorizationId = this.authorizationId,
    status = PaymentStatus.valueOf(this.status),
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
)
