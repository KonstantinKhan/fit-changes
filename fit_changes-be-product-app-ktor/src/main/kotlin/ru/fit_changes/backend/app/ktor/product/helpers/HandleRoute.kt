package ru.fit_changes.backend.app.ktor.product.helpers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.openapi.models.BaseMessage

suspend inline fun <reified T : BaseMessage, reified U : BaseMessage> ApplicationCall.handleRoute(
    block: BeContext.(T) -> U
) {
    val request = receive<BaseMessage>() as T
    val context = BeContext()
    try {
        val response = context.block(request)
        respond(response)
    } catch (e: Exception) {
        println("Exception: ${e.message}")
    }
}