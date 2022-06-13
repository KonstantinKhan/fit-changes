package ru.fit_changes.backend.common.mappers

import ru.fit_changes.backend.common.models.IError
import ru.fit_changes.openapi.models.RequestError

fun IError.toTransport() = RequestError(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() }
)