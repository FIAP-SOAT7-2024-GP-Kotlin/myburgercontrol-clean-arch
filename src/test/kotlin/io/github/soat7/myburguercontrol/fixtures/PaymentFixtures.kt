package io.github.soat7.myburguercontrol.fixtures

import io.github.soat7.myburguercontrol.business.enum.PaymentStatus
import io.github.soat7.myburguercontrol.business.model.Payment
import java.util.UUID

object PaymentFixtures {
    fun mockPayment(): Payment {
        return Payment(
            id = UUID.randomUUID(),
            status = PaymentStatus.REQUESTED,
            authorizationId = null,
        )
    }
}
