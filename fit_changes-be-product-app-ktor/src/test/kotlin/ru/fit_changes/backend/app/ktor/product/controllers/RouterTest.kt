package ru.fit_changes.backend.app.ktor.product.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.fit_changes.backend.app.ktor.product.module
import ru.fit_changes.openapi.models.BaseMessage
import kotlin.test.assertEquals

abstract class RouterTest {
    protected inline fun <reified T> testPostRequest(
        body: BaseMessage? = null,
        uri: String,
        result: HttpStatusCode = HttpStatusCode.OK,
        crossinline block: T.() -> Unit
    ) {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, uri) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.withCharset(Charsets.UTF_8).toString())
                setBody(jacksonObjectMapper().writeValueAsString(body))
            }.apply {
                println("content: ${response.content}")
                assertEquals(result, response.status())
            }
        }
    }
}