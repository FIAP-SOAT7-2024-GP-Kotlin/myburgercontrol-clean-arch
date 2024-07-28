package io.github.soat7.myburguercontrol.external.webservice.notification.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class WebhookEvent(
    @JsonProperty("action") val action: String,
    @JsonProperty("api_version") val api_version: String,
    @JsonProperty("data") val data: Data,
    @JsonProperty("date_created") val date_created: String,
    @JsonProperty("id") val id: String,
    @JsonProperty("live_mode") val live_mode: Boolean,
    @JsonProperty("type") val type: String,
    @JsonProperty("user_id") val user_id: Int,
) {
    data class Data(
        @JsonProperty("id") val id: String,
    )
}
