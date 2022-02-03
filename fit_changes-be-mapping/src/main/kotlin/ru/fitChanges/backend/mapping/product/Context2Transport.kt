package ru.fitChanges.backend.mapping.product

import ru.fit_changes.backend.common.models.IError
import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.product.models.ProductModel
import ru.fitChanges.openapi.models.CreateProductResponse
import ru.fitChanges.openapi.models.Permissions
import ru.fitChanges.openapi.models.RequestError
import ru.fitChanges.openapi.models.ResponseProduct

fun BeContext.toCreateProductResponse() = CreateProductResponse(
    messageType = "CreateProductResponse",
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) CreateProductResponse.Result.SUCCESS
    else CreateProductResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    createProduct = responseProduct.takeIf { it != ProductModel() }?.toTransport()
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
    carbohydratePerHundredGrams = carbohydratesPerHundredGrams,
    productId = productId.takeIf { it != ru.fit_changes.backend.common.product.models.ProductIdModel.NONE }?.asString(),
    permissions = permissions.takeIf { it.isNotEmpty() }?.map { Permissions.valueOf(it.name) }?.toSet()
)