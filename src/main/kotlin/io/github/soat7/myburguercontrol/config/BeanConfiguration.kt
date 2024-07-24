package io.github.soat7.myburguercontrol.config

import io.github.soat7.myburguercontrol.domain.repository.PaymentIntegrationRepository
import io.github.soat7.myburguercontrol.domain.usecase.AuthenticationUseCase
import io.github.soat7.myburguercontrol.domain.usecase.CustomUserDetailsUseCase
import io.github.soat7.myburguercontrol.domain.usecase.CustomerUseCase
import io.github.soat7.myburguercontrol.domain.usecase.OrderUseCase
import io.github.soat7.myburguercontrol.domain.usecase.PaymentUseCase
import io.github.soat7.myburguercontrol.domain.usecase.ProductUseCase
import io.github.soat7.myburguercontrol.domain.usecase.TokenUseCase
import io.github.soat7.myburguercontrol.domain.usecase.UserUseCase
import io.github.soat7.myburguercontrol.infrastructure.customer.CustomerGateway
import io.github.soat7.myburguercontrol.infrastructure.order.OrderGateway
import io.github.soat7.myburguercontrol.infrastructure.payment.PaymentGateway
import io.github.soat7.myburguercontrol.infrastructure.product.ProductGateway
import io.github.soat7.myburguercontrol.infrastructure.user.UserGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class BeanConfiguration {

    @Bean
    fun authenticationUseCase(
        authenticationManager: AuthenticationManager,
        customUserDetailsUseCase: CustomUserDetailsUseCase,
        tokenUseCase: TokenUseCase,
        jwtProperties: JwtProperties,
    ) = AuthenticationUseCase(
        authManager = authenticationManager,
        userDetailsService = customUserDetailsUseCase,
        tokenUseCase = tokenUseCase,
        jwtProperties = jwtProperties,
    )

    @Bean
    fun customerUseCase(
        customerGateway: CustomerGateway,
    ) = CustomerUseCase(
        customerGateway = customerGateway,
    )

    @Bean
    fun customUserDetailsUseCase(
        userGateway: UserGateway,
    ) = CustomUserDetailsUseCase(
        userGateway = userGateway,
    )

    @Bean
    fun orderUseCase(
        orderGateway: OrderGateway,
        customerUseCase: CustomerUseCase,
        productUseCase: ProductUseCase,
        paymentUseCase: PaymentUseCase,
    ) = OrderUseCase(
        orderGateway = orderGateway,
        customerUseCase = customerUseCase,
        productUseCase = productUseCase,
        paymentUseCase = paymentUseCase,
    )

    @Bean
    fun paymentService(
        paymentIntegrationRepository: PaymentIntegrationRepository,
        paymentGateway: PaymentGateway,
    ) = PaymentUseCase(
        paymentIntegrationRepository = paymentIntegrationRepository,
        paymentGateway = paymentGateway,
    )

    @Bean
    fun productService(
        productGateway: ProductGateway,
    ) = ProductUseCase(
        productGateway = productGateway,
    )

    @Bean
    fun tokenUseCase(
        jwtProperties: JwtProperties,
    ) = TokenUseCase(
        jwtProperties = jwtProperties,
    )

    @Bean
    fun userUseCase(
        userGateway: UserGateway,
        encoder: PasswordEncoder,
    ) = UserUseCase(
        userGateway = userGateway,
        encoder = encoder,
    )
}
