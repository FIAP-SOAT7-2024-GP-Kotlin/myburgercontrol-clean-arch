package io.github.soat7.myburguercontrol.external.webservice.auth.api

data class AuthRequest(
    val cpf: String,
    val password: String,
)
