package io.github.soat7.myburguercontrol.fixtures

import io.github.soat7.myburguercontrol.domain.entities.Payment
import io.github.soat7.myburguercontrol.domain.entities.enum.PaymentStatus
import io.github.soat7.myburguercontrol.external.thirdparty.api.QRCodeData
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

object QRCodeFixtures {
    fun mockQRCode(orderId: String) = QRCodeData("QRCodeData", orderId)
}
