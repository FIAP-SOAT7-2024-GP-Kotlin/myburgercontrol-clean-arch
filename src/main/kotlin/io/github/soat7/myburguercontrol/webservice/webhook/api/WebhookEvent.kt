package io.github.soat7.myburguercontrol.webservice.webhook.api

data class WebhookEvent(
    val action: String,
    val api_version: String,
    val data: Data,
    val date_created: String,
    val id: String,
    val live_mode: Boolean,
    val type: String,
    val user_id: Int,
) {
    data class Data(
        val id: String,
    )
}
