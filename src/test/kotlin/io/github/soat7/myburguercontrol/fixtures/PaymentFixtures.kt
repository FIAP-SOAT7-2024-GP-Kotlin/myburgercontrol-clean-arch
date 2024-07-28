package io.github.soat7.myburguercontrol.fixtures

import io.github.soat7.myburguercontrol.domain.entities.Payment
import io.github.soat7.myburguercontrol.domain.entities.enum.PaymentStatus
import io.github.soat7.myburguercontrol.external.thirdparty.api.PaymentResult
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

object PaymentResultFixtures {
    fun mockPaymentResult(authorizationId: String?, approved: Boolean) =
        PaymentResult(authorizationId, approved)
}
