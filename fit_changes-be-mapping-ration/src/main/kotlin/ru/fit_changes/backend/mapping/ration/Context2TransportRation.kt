package ru.fit_changes.backend.mapping.ration

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.mappers.toTransport
import ru.fit_changes.backend.common.models.IError
import ru.fit_changes.backend.common.models.ration.RationModel
import ru.fit_changes.openapi.models.*

fun BeContextRation.toCreateRationResponse() = CreateRationResponse(
    messageType = "CreateRationResponse",
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) CreateRationResponse.Result.SUCCESS
    else CreateRationResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    createdRation = responseRation.takeIf { errors.isEmpty() && it != RationModel() }?.toTransport()
)

fun BeContextRation.toReadRationResponse() = ReadRationResponse(
    messageType = "ReadRationResponse",
    requestId = requestId,
    result = if (errors.find { it.level == IError.Level.ERROR } == null) ReadRationResponse.Result.SUCCESS
    else ReadRationResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    readRation = responseRation.takeIf { errors.isEmpty() && it != RationModel() }?.toTransport()
)

fun BeContextRation.toUpdateRationResponse() = UpdateRationResponse(
    messageType = "UpdateRationResponse",
    requestId = requestId,
    result = if (errors.find { it.level == IError.Level.ERROR } == null) UpdateRationResponse.Result.SUCCESS
    else UpdateRationResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    updatedRation = responseRation.takeIf { errors.isEmpty() && it != RationModel() }?.toTransport()
)

fun BeContextRation.toDeleteRationResponse() = DeleteRationResponse(
    messageType = "DeleteRationResponse",
    requestId = requestId,
    result = if (errors.find { it.level == IError.Level.ERROR } == null) DeleteRationResponse.Result.SUCCESS
    else DeleteRationResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    deletedRation = responseRation.takeIf { errors.isEmpty() && it != RationModel() }?.toTransport()
)

fun BeContextRation.toSearchRationResponse() = SearchRationResponse(
    messageType = "SearchRationResponse",
    requestId = requestId,
    result = if (errors.find { it.level == IError.Level.ERROR } == null) SearchRationResponse.Result.SUCCESS
    else SearchRationResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    foundRations = foundRations.map { it.toTransport() }
)