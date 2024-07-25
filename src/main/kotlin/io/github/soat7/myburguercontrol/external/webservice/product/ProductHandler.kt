package io.github.soat7.myburguercontrol.external.webservice.product

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.soat7.myburguercontrol.adapters.mapper.toDomain
import io.github.soat7.myburguercontrol.adapters.mapper.toResponse
import io.github.soat7.myburguercontrol.domain.usecase.ProductUseCase
import io.github.soat7.myburguercontrol.external.webservice.common.PaginatedResponse
import io.github.soat7.myburguercontrol.external.webservice.product.api.ProductCreationRequest
import io.github.soat7.myburguercontrol.external.webservice.product.api.ProductResponse
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import java.util.UUID

private val logger = KotlinLogging.logger {}

class ProductHandler(
    private val productUseCase: ProductUseCase,
) {

    fun create(request: ProductCreationRequest): ResponseEntity<ProductResponse> {
        logger.debug { "Creating product" }
        val product = productUseCase.create(request.toDomain())
        return ResponseEntity.ok(product.toResponse())
    }

    fun getById(id: UUID): ResponseEntity<ProductResponse> {
        logger.debug { "Getting product by id" }
        return productUseCase.findById(id)?.let {
            ResponseEntity.ok(it.toResponse())
        } ?: ResponseEntity.notFound().build()
    }

    fun getByProductType(productType: String): ResponseEntity<List<ProductResponse>> {
        logger.debug { "Getting product by product type [$productType]" }
        return ResponseEntity.ok(productUseCase.findByType(productType).map { it.toResponse() })
    }

    fun findAll(page: Int, size: Int): ResponseEntity<PaginatedResponse<ProductResponse>> {
        val pageable = PageRequest.of(page, size)

        val response = productUseCase.findAll(pageable)

        return ResponseEntity.ok(
            PaginatedResponse(
                content = response.content.map { it.toResponse() },
                totalPages = response.totalPages,
                totalElements = response.totalElements,
                currentPage = response.number,
                pageSize = response.size,
            ),
        )
    }

    fun delete(id: UUID): ResponseEntity<Void> {
        logger.debug { "Deleting product with id: [$id]" }
        productUseCase.delete(id)
        return ResponseEntity.noContent().build()
    }
}
