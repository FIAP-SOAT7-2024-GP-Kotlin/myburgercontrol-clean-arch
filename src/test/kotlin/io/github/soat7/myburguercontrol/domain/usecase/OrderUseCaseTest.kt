package io.github.soat7.myburguercontrol.domain.usecase

import io.github.soat7.myburguercontrol.domain.enum.OrderStatus
import io.github.soat7.myburguercontrol.domain.repository.OrderRepository
import io.github.soat7.myburguercontrol.fixtures.CustomerFixtures.mockDomainCustomer
import io.github.soat7.myburguercontrol.fixtures.OrderDetailFixtures
import io.github.soat7.myburguercontrol.fixtures.PaymentFixtures.mockPayment
import io.github.soat7.myburguercontrol.fixtures.ProductFixtures
import io.github.soat7.myburguercontrol.util.toBigDecimal
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import java.util.UUID
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import io.github.soat7.myburguercontrol.domain.model.Order as OrderModel

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class OrderUseCaseTest {

    private val repository = mockk<OrderRepository>()
    private val customerUseCase = mockk<CustomerUseCase>()
    private val productUseCase = mockk<ProductUseCase>()
    private val paymentUseCase = mockk<PaymentUseCase>()
    private val service = OrderUseCase(repository, customerUseCase, productUseCase, paymentUseCase)

    @BeforeTest
    fun setUp() {
        clearMocks(customerUseCase)
        clearMocks(repository)
    }

    @Test
    @Order(1)
    fun `should create a new order using cpf`() {
        val cpf = "23282711034"
        val customer = mockDomainCustomer(cpf = cpf)
        val product = ProductFixtures.mockDomainProduct()
        val payment = mockPayment()

        every { customerUseCase.findCustomerByCpf(cpf) } returns customer
        every { repository.create(any<OrderModel>()) } answers {
            (this.firstArg() as OrderModel).copy(id = UUID.randomUUID())
        }
        every { productUseCase.findById(any()) } returns product
        every {
            paymentUseCase.requestPayment(any())
        } returns payment
        every { paymentUseCase.createPayment() } returns payment
        every { repository.update(any<OrderModel>()) } answers {
            (this.firstArg() as OrderModel).copy(id = UUID.randomUUID())
        }

        val order = service.createOrder(OrderDetailFixtures.mockOrderDetail(cpf = cpf, product = product))

        verify(exactly = 1) { customerUseCase.findCustomerByCpf(any()) }
        verify(exactly = 2) { repository.update(any()) }
        verify(exactly = 1) { repository.create(any()) }
        verify(exactly = 1) { paymentUseCase.createPayment() }
        verify(exactly = 1) { paymentUseCase.requestPayment(any()) }

        assertNotNull(order.id)
        assertEquals(cpf, order.customer.cpf)
        assertEquals(OrderStatus.RECEIVED, order.status)
        assertFalse(order.items.isEmpty())
        assertEquals(1.0.toBigDecimal(), order.total)
    }
}
