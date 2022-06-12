package ru.fit_changes.backend.mapping.ration

import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.models.*
import ru.fit_changes.backend.common.models.ration.BeMeal
import ru.fit_changes.backend.common.models.ration.BeUsedProduct
import ru.fit_changes.backend.common.models.ration.RationModel
import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.openapi.models.CreatableRation
import ru.fit_changes.openapi.models.CreateRationRequest
import ru.fit_changes.openapi.models.Meal
import ru.fit_changes.openapi.models.UsedProduct
import java.time.Instant

fun BeContextRation.setQuery(query: CreateRationRequest) = apply {
    requestId = query.requestId ?: ""
    requestRation = query.createRation?.toModel() ?: RationModel()
}

private fun CreatableRation.toModel() = RationModel(
    authorId = authorId?.let { AuthorIdModel(it) } ?: AuthorIdModel.NONE,
    dateRation = dateRation?.let { Instant.parse(it) } ?: Instant.now(),
    caloriesNorm = caloriesNorm?.let { CaloriesModel(it) } ?: CaloriesModel.NONE,
    proteinsNorm = proteinsNorm?.let { ProteinsModel(it) } ?: ProteinsModel.NONE,
    fatsNorm = fatsNorm?.let { FatsModel(it) } ?: FatsModel.NONE,
    carbohydratesNorm = carbohydratesNorm?.let { CarbohydratesModel(it) } ?: CarbohydratesModel.NONE,
    caloriesFact = caloriesFact?.let { CaloriesModel(it) } ?: CaloriesModel.NONE,
    proteinsFact = proteinsFact?.let { ProteinsModel(it) } ?: ProteinsModel.NONE,
    fatsFact = fatsFact?.let { FatsModel(it) } ?: FatsModel.NONE,
    carbohydratesFact = carbohydratesFact?.let { CarbohydratesModel(it) } ?: CarbohydratesModel.NONE,
    meals = meals.takeIf { !it.isNullOrEmpty() }?.map { it.toModel() }?.toMutableList() ?: mutableListOf(),
)

fun Meal.toModel() = BeMeal(
    mealName = "",
    usedProducts = products.takeIf { !it.isNullOrEmpty() }?.map { it.toModel() }?.toMutableList() ?: mutableListOf()
)

private fun UsedProduct.toModel() = BeUsedProduct(
    authorId = authorId?.let { AuthorIdModel(it) } ?: AuthorIdModel.NONE,
    category = category ?: "",
    productName = productName ?: "",
    caloriesPerHundredGrams = caloriesPerHundredGrams?.let { CaloriesModel(it) } ?: CaloriesModel.NONE,
    proteinsPerHundredGrams = proteinsPerHundredGrams?.let { ProteinsModel(it) } ?: ProteinsModel.NONE,
    fatsPerHundredGrams = fatsPerHundredGrams?.let { FatsModel(it) } ?: FatsModel.NONE,
    carbohydratesPerHundredGrams = carbohydratesPerHundredGrams?.let { CarbohydratesModel(it) }
        ?: CarbohydratesModel.NONE,
    productId = productId?.let { ProductIdModel(it) } ?: ProductIdModel.NONE,
    weight = weight?.takeIf { it > 0.0 } ?: 0.0,
    caloriesFact = caloriesFact?.let { CaloriesModel(it) } ?: CaloriesModel.NONE,
    proteinsFact = proteinsFact?.let { ProteinsModel(it) } ?: ProteinsModel.NONE,
    fatsFact = fatsFact?.let { FatsModel(it) } ?: FatsModel.NONE,
    carbohydratesFact = carbohydratesFact?.let { CarbohydratesModel(it) } ?: CarbohydratesModel.NONE,
    parentProductId = parentProductId?.let { ProductIdModel(it) } ?: ProductIdModel.NONE
)
