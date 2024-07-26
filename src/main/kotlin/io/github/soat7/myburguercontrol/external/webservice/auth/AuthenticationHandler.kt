package io.github.soat7.myburguercontrol.external.webservice.auth

import io.github.soat7.myburguercontrol.domain.usecase.AuthenticationUseCase
import io.github.soat7.myburguercontrol.external.webservice.auth.api.AuthenticationRequest
import io.github.soat7.myburguercontrol.external.webservice.auth.api.AuthenticationResponse
import org.springframework.http.ResponseEntity

class AuthenticationHandler(
    private val authenticationUseCase: AuthenticationUseCase,
) {

    fun authenticate(request: AuthenticationRequest): ResponseEntity<AuthenticationResponse> {
        val authentication = authenticationUseCase.authenticate(request)
        return ResponseEntity.ok(authentication)
    }
}
