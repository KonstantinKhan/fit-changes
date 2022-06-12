package ru.fit_changes.backend.mapping.product

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.models.*
import ru.fit_changes.backend.common.product.models.*
import ru.fit_changes.openapi.models.*

fun BeContext.toCreateProductResponse() = CreateProductResponse(
    messageType = "CreateProductResponse",
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) CreateProductResponse.Result.SUCCESS
    else CreateProductResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    createdProduct = responseProduct.takeIf {
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

fun BeContext.toUpdateProductResponse() = UpdateProductResponse(
    messageType = "UpdateProductResponse",
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) UpdateProductResponse.Result.SUCCESS
    else UpdateProductResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    updateProduct = responseProduct.takeIf {
        errors.isEmpty() &&
                it != ProductModel()
    }?.toTransport()
)

fun BeContext.toDeleteProductResponse() = DeleteProductResponse(
    messageType = "DeleteProductResponse",
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) DeleteProductResponse.Result.SUCCESS
    else DeleteProductResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    deletedProduct = responseProduct.takeIf {
        errors.isEmpty() && it != ProductModel()
    }?.toTransport()
)

fun BeContext.toSearchProductResponse() = SearchProductResponse(
    messageType = "SearchProductResponse",
    requestId = requestId.takeIf { it.isNotBlank() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) SearchProductResponse.Result.SUCCESS
    else SearchProductResponse.Result.ERROR,
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    foundProducts = foundProducts.takeIf { it.isNotEmpty() }?.map { it.toTransport() }
)

private fun IError.toTransport() = RequestError(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() }
)

fun ProductModel.toTransport() = ResponseProduct(
    productName = productName.takeIf { it.isNotBlank() },
    caloriesPerHundredGrams = caloriesPerHundredGrams.takeIf { it != CaloriesModel.NONE }?.value,
    proteinsPerHundredGrams = proteinsPerHundredGrams.takeIf { it != ProteinsModel.NONE }?.value,
    fatsPerHundredGrams = fatsPerHundredGrams.takeIf { it != FatsModel.NONE }?.value,
    carbohydratesPerHundredGrams = carbohydratesPerHundredGrams.takeIf { it != CarbohydratesModel.NONE }?.value,
    productId = productId.takeIf { it != ProductIdModel.NONE }?.asString(),
    permissions = permissions.takeIf { it.isNotEmpty() }?.map { Permissions.valueOf(it.name) }?.toSet(),
    authorId = authorId.takeIf { it != AuthorIdModel.NONE }?.asString(),
)