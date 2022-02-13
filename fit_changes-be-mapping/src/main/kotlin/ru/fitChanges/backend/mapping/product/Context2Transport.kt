package ru.fitChanges.backend.mapping.product

import ru.fitChanges.openapi.models.*
import ru.fit_changes.backend.common.models.IError
import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.product.models.ProductModel

fun BeContext.toCreateProductResponse() = CreateProductResponse(
    messageType = "CreateProductResponse",
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) CreateProductResponse.Result.SUCCESS
    else CreateProductResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    createProduct = responseProduct.takeIf {
        errors.isEmpty() &&
                it != ProductModel()
    }?.toTransport()
)

fun BeContext.toReadProductResponse() = ReadProductResponse(
    messageType = "ReadProductResponse",
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) ReadProductResponse.Result.SUCCESS
    else ReadProductResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    readProduct = responseProduct.takeIf {
        errors.isEmpty() &&
                it != ProductModel()
    }?.toTransport()
)

private fun IError.toTransport() = RequestError(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() }
)

fun ProductModel.toTransport() = ResponseProduct(
    productName = productName.takeIf { it.isNotBlank() },
    caloriesPerHundredGrams = caloriesPerHundredGrams,
    proteinsPerHundredGrams = proteinsPerHundredGrams,
    fatsPerHundredGrams = fatsPerHundredGrams,
    carbohydratesPerHundredGrams = carbohydratesPerHundredGrams,
    productId = productId.takeIf { it != ru.fit_changes.backend.common.product.models.ProductIdModel.NONE }?.asString(),
    permissions = permissions.takeIf { it.isNotEmpty() }?.map { Permissions.valueOf(it.name) }?.toSet()
)