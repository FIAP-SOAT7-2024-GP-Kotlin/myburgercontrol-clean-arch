package io.github.soat7.myburguercontrol.external.webservice.config

import io.github.soat7.myburguercontrol.domain.usecase.AuthenticationUseCase
import io.github.soat7.myburguercontrol.domain.usecase.CustomerUseCase
import io.github.soat7.myburguercontrol.domain.usecase.OrderUseCase
import io.github.soat7.myburguercontrol.domain.usecase.ProductUseCase
import io.github.soat7.myburguercontrol.domain.usecase.UserUseCase
import io.github.soat7.myburguercontrol.external.webservice.auth.AuthenticationHandler
import io.github.soat7.myburguercontrol.external.webservice.customer.CustomerHandler
import io.github.soat7.myburguercontrol.external.webservice.order.OrderHandler
import io.github.soat7.myburguercontrol.external.webservice.product.ProductHandler
import io.github.soat7.myburguercontrol.external.webservice.user.UserHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebServiceBeanConfiguration {

    @Bean
    fun customerHandler(customerUseCase: CustomerUseCase) = CustomerHandler(customerUseCase = customerUseCase)

    @Bean
    fun productHandler(productUseCase: ProductUseCase) = ProductHandler(productUseCase = productUseCase)

    @Bean
    fun orderHandler(orderUseCase: OrderUseCase) = OrderHandler(orderUseCase = orderUseCase)

    @Bean
    fun userHandler(userUseCase: UserUseCase) = UserHandler(userUseCase = userUseCase)

    @Bean
    fun authHandler(authenticationUseCase: AuthenticationUseCase) = AuthenticationHandler(authenticationUseCase = authenticationUseCase)
}
