package io.github.soat7.myburguercontrol.adapters.mapper

import io.github.soat7.myburguercontrol.domain.entities.Product
import io.github.soat7.myburguercontrol.domain.entities.enum.ProductType
import io.github.soat7.myburguercontrol.external.db.product.entity.ProductEntity
import io.github.soat7.myburguercontrol.external.webservice.order.api.OrderItemResponse
import io.github.soat7.myburguercontrol.external.webservice.product.api.ProductCreationRequest
import io.github.soat7.myburguercontrol.external.webservice.product.api.ProductResponse
import java.time.Instant
import java.util.UUID

fun Product.toOrderItemProductResponse() = OrderItemResponse.OrderItemProductResponse(
    name = this.name,
    productId = this.id,
    price = this.price,
)

fun Product.toResponse() = ProductResponse(
    id = this.id,
    name = this.name,
    description = this.description,
    price = this.price,
    type = this.type.name,
)

fun Product.toPersistence() = ProductEntity(
    id = this.id,
    name = this.name,
    description = this.description,
    price = this.price,
    type = this.type.name,
    createdAt = Instant.now(),
    updatedAt = Instant.now(),
)

fun ProductCreationRequest.toDomain() = Product(
    id = UUID.randomUUID(),
    name = this.name,
    description = this.description,
    price = this.price,
    type = ProductType.valueOf(this.type.name),
)

fun ProductEntity.toDomain() = Product(
    id = this.id!!,
    name = this.name,
    description = this.description,
    price = this.price,
    type = ProductType.from(this.type),
)
