package io.github.soat7.myburguercontrol.webservice.notification.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MerchantOrderResponse(
    @JsonProperty("order_status") val orderStatus: String,
    @JsonProperty("external_reference") val externalReference: String? = null,
)
