package ru.fit_changes.backend.app.ktor.ration.helpers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.openapi.models.BaseMessage

suspend inline fun <reified T : BaseMessage, reified U : BaseMessage> ApplicationCall.handleRoutes(
    block: BeContextRation.(T) -> U
) {
    val request = receive<BaseMessage>() as T
    println("request: $request")
    val context = BeContextRation()

    val response = context.block(request)
    println("createdRation: ${context.responseRation}")
    println("response: $response")
    respond(response)
}