package ru.fit_changes.backend.app.ktor.product.helpers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.fit_changes.backend.app.ktor.product.mappers.toModel
import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.openapi.models.BaseMessage

suspend inline fun <reified T : BaseMessage, reified U : BaseMessage> ApplicationCall.handleRoute(
    block: BeContext.(T) -> U
) {
    val request = receive<BaseMessage>() as T
    val context = BeContext(
        principal = principal<JWTPrincipal>().toModel()
    )
    try {
        val response = context.block(request)
        val status: HttpStatusCode = if (context.errors.isEmpty()) HttpStatusCode.OK else HttpStatusCode.Forbidden
        respond(status, response)
    } catch (e: Exception) {
        println("Exception: ${e.message}")
    }
}