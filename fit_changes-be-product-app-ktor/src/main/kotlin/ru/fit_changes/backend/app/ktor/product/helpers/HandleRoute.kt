package ru.fit_changes.backend.app.ktor.product.helpers

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.fitChanges.openapi.models.BaseMessage
import ru.fit_changes.backend.common.context.BeContext

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