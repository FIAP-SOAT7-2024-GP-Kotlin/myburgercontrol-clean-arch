package io.github.soat7.myburguercontrol.webservice

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.soat7.myburguercontrol.base.BaseIntegrationTest
import io.github.soat7.myburguercontrol.fixtures.AuthFixtures
import io.github.soat7.myburguercontrol.fixtures.UserFixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ProblemDetail
import kotlin.test.assertTrue

class AuthIT : BaseIntegrationTest() {

    @Test
    fun `should successfully authenticate`() {
        val cpf = "29257035859"
        val password = "pass123"
        userJpaRepository.save(UserFixtures.mockUserEntity(cpf = cpf, password = passwordEncoder.encode(password)))

        val inputAuthData = AuthFixtures.mockAuthCreationRequest(cpf, password)

        val response = restTemplate.postForEntity<String>("/auth", inputAuthData)

        assertTrue(response.statusCode.is2xxSuccessful)
        assertNotNull(response.body)
        assertTrue(response.body!!.contains("access_token"))
    }

    @Test
    fun `should return BAD CREDENTIALS when password is wrong`() {
        val objectMapper = jacksonObjectMapper()
        val cpf = "29257035859"
        val password = "pass123"
        userJpaRepository.save(UserFixtures.mockUserEntity(cpf = cpf, password = passwordEncoder.encode(password)))

        val inputAuthData = AuthFixtures.mockAuthCreationRequest(cpf, "wrongPass")

        val response = restTemplate.exchange<String>(
            url = "/auth",
            method = HttpMethod.POST,
            requestEntity = HttpEntity(inputAuthData, null),
        )

        assertTrue(response.statusCode.is4xxClientError)
        assertNotNull(response.body)
        assertThat(
            objectMapper.readValue(
                response.body,
                ProblemDetail::class.java,
            ).detail!!.contains("Bad Credentials"),
        )
    }
}
