package ru.fit_changes.backend.mapping.ration

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.mappers.toTransport
import ru.fit_changes.backend.common.models.IError
import ru.fit_changes.backend.common.models.ration.RationModel
import ru.fit_changes.openapi.models.CreateRationResponse

fun BeContextRation.toCreateRationResponse() = CreateRationResponse(
    messageType = "CreateRationResponse",
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) CreateRationResponse.Result.SUCCESS
    else CreateRationResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    createdRation = responseRation.takeIf { errors.isEmpty() && it != RationModel() }?.toTransport()
)