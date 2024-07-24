package io.github.soat7.myburguercontrol.webservice.config

import io.github.soat7.myburguercontrol.domain.usecase.CustomerUseCase
import io.github.soat7.myburguercontrol.domain.usecase.ProductUseCase
import io.github.soat7.myburguercontrol.webservice.customer.CustomerHandler
import io.github.soat7.myburguercontrol.webservice.product.ProductHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebServiceBeanConfiguration {

    @Bean
    fun customerHandler(
        customerUseCase: CustomerUseCase,
    ) = CustomerHandler(
        customerUseCase = customerUseCase,
    )

    @Bean
    fun productHandler(
        productUseCase: ProductUseCase,
    ) = ProductHandler(
        productUseCase = productUseCase,
    )
}
