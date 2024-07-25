package io.github.soat7.myburguercontrol.external.webservice.auth

import io.github.soat7.myburguercontrol.domain.usecase.AuthenticationUseCase
import io.github.soat7.myburguercontrol.external.webservice.auth.api.AuthRequest
import io.github.soat7.myburguercontrol.external.webservice.auth.api.AuthResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    path = ["/auth"],
    produces = [MediaType.APPLICATION_JSON_VALUE],
    consumes = [MediaType.APPLICATION_JSON_VALUE],
)
class AuthController(
    private val authenticationUseCase: AuthenticationUseCase,
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        tags = ["0 - Jornada de Autenticação"],
        summary = "Utilize esta rota para autenticar um usuário já criado",
        description = "Utilize esta rota para autenticar um usuário já criado",
        operationId = "auth_3",
    )
    fun authenticate(@RequestBody authRequest: AuthRequest): AuthResponse =
        authenticationUseCase.authenticate(authRequest)
}
