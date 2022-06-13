package ru.fit_changes.backend.common.mappers

import ru.fit_changes.backend.common.models.*
import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.backend.common.product.models.ProductModel
import ru.fit_changes.openapi.models.Permissions
import ru.fit_changes.openapi.models.ResponseProduct

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