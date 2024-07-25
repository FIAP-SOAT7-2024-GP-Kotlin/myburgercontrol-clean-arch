package io.github.soat7.myburguercontrol.domain.usecase

import io.github.soat7.myburguercontrol.config.JwtProperties
import io.github.soat7.myburguercontrol.external.webservice.auth.api.AuthRequest
import io.github.soat7.myburguercontrol.external.webservice.auth.api.AuthResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import java.util.Date

class AuthenticationUseCase(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsUseCase,
    private val tokenUseCase: TokenUseCase,
    private val jwtProperties: JwtProperties,
) {

    fun authenticate(authRequest: AuthRequest): AuthResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authRequest.cpf,
                authRequest.password,
            ),
        )
        val user = userDetailsService.loadUserByUsername(authRequest.cpf)

        val accessToken = tokenUseCase.generate(
            userDetails = user,
            expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration),
        )

        return AuthResponse(accessToken)
    }
}
