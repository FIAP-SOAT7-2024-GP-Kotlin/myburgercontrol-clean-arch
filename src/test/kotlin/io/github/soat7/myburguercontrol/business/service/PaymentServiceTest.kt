package io.github.soat7.myburguercontrol.business.service

import io.github.soat7.myburguercontrol.business.repository.OrderRepository
import io.github.soat7.myburguercontrol.business.repository.PaymentIntegrationRepository
import io.github.soat7.myburguercontrol.business.repository.PaymentRepository
import io.mockk.clearMocks
import io.mockk.mockk
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import kotlin.test.BeforeTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class PaymentServiceTest {

    private val paymentIntegrationRepository = mockk<PaymentIntegrationRepository>()
    private val paymentRepository = mockk<PaymentRepository>()
    private val orderRepository = mockk<OrderRepository>()
    private val service = PaymentService(paymentIntegrationRepository, paymentRepository, orderRepository)

    @BeforeTest
    fun setUp() {
        clearMocks(paymentIntegrationRepository)
    }
}
