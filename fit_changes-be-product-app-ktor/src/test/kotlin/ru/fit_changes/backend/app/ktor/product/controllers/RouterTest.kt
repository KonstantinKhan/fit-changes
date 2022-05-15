package ru.fit_changes.backend.app.ktor.product.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.utils.io.charsets.*
import ru.fit_changes.backend.app.ktor.product.configs.KtorAuthConfig
import ru.fit_changes.openapi.models.BaseMessage
import kotlin.test.assertEquals

abstract class RouterTest {
    protected inline fun <reified T> testPostRequest(
        body: BaseMessage? = null,
        uri: String,
        authConfig: KtorAuthConfig = KtorAuthConfig.TEST,
        yearExpiration: Int = 2023,
        groups: Array<String> = arrayOf("USER", "TEST"),
        result: HttpStatusCode = HttpStatusCode.OK,
        crossinline block: T.() -> Unit = {}
    ) {
        testApplication {
            val response = client.post(uri) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                bearerAuth(KtorAuthConfig.testUserToken(authConfig, yearExpiration, *groups))
                setBody(jacksonObjectMapper().writeValueAsString(body))
            }
            assertEquals(result, response.status)
            if (result == HttpStatusCode.OK) {
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                jacksonObjectMapper().readValue(response.bodyAsText(), T::class.java).block()
            }
        }
    }
}