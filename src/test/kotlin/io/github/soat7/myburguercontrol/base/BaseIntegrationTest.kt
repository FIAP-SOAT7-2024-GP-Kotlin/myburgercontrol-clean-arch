package io.github.soat7.myburguercontrol.base

import io.github.soat7.myburguercontrol.Application
import io.github.soat7.myburguercontrol.container.PostgresContainer
import io.github.soat7.myburguercontrol.domain.model.Role
import io.github.soat7.myburguercontrol.fixtures.AuthFixtures
import io.github.soat7.myburguercontrol.fixtures.UserFixtures
import io.github.soat7.myburguercontrol.infrastructure.persistence.user.repository.UserRepository
import io.github.soat7.myburguercontrol.infrastructure.rest.api.AuthResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.util.UUID

@ActiveProfiles("test")
@SpringBootTest(
    classes = [Application::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ExtendWith(PostgresContainer::class)
class BaseIntegrationTest {

    @Autowired
    protected lateinit var restTemplate: TestRestTemplate

    @Autowired
    protected lateinit var userRepository: UserRepository

    @Autowired
    protected lateinit var passwordEncoder: PasswordEncoder

    @LocalServerPort
    private var port: Int = 0

    protected lateinit var authenticationHeader: MultiValueMap<String, String>

    @BeforeEach
    fun setUpAuthentication() {
        println("Cleaning User database...")
        userRepository.deleteAll()
        authenticationHeader = buildAuthentication()
    }

    protected fun buildAuthentication(): MultiValueMap<String, String> {
        val cpf = "15666127055"
        val password = UUID.randomUUID().toString()
        val userRole = Role.ADMIN
        userRepository.save(
            UserFixtures.mockUserEntity(
                cpf = cpf,
                password = passwordEncoder.encode(password),
                role = userRole
            )
        )

        val response = restTemplate.postForEntity<AuthResponse>(
            "/auth",
            AuthFixtures.mockAuthCreationRequest(cpf, password)
        ).body
            ?: throw RuntimeException("Failed to authenticate")

        val header: MultiValueMap<String, String> = LinkedMultiValueMap()
        header.add("Authorization", "Bearer ${response.accessToken}")
        header.add("Content-Type", MediaType.APPLICATION_JSON_VALUE)

        return header
    }
}
