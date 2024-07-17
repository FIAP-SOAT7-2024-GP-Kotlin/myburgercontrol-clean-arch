package io.github.soat7.myburguercontrol.business.mapper

import io.github.soat7.myburguercontrol.business.enum.PaymentStatus
import io.github.soat7.myburguercontrol.business.model.Payment
import io.github.soat7.myburguercontrol.database.payment.entity.PaymentEntity
import io.github.soat7.myburguercontrol.webservice.payment.api.PaymentStatusResponse

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

fun Payment.toPaymentStatusResponse() = PaymentStatusResponse(
    id = this.id.toString(),
    status = this.status.toString(),
)
