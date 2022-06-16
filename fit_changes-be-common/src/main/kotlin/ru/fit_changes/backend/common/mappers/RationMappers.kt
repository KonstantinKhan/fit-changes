package ru.fit_changes.backend.common.mappers

import ru.fit_changes.backend.common.models.*
import ru.fit_changes.backend.common.models.enums.BeMealName
import ru.fit_changes.backend.common.models.ration.BeMeal
import ru.fit_changes.backend.common.models.ration.BeUsedProduct
import ru.fit_changes.backend.common.models.ration.RationIdModel
import ru.fit_changes.backend.common.models.ration.RationModel
import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.openapi.models.*
import java.time.Instant

fun CreatableRation.toModel() = RationModel(
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

fun UpdatableRation.toModel() = RationModel(
    rationId = rationId?.let { RationIdModel(it) } ?: RationIdModel.NONE,
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
    mealName = mealName?.name?.let { BeMealName.valueOf(it) } ?: BeMealName.NONE,
    usedProducts = products.takeIf { !it.isNullOrEmpty() }?.map { it.toModel() }?.toMutableList() ?: mutableListOf()
)

fun UsedProduct.toModel() = BeUsedProduct(
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

fun RationModel.toTransport() = ResponseRation(
    authorId = authorId.takeIf { it != AuthorIdModel.NONE }?.asString(),
    dateRation = dateRation.toString(),
    caloriesNorm = caloriesNorm.takeIf { it != CaloriesModel.NONE }?.value,
    proteinsNorm = proteinsNorm.takeIf { it != ProteinsModel.NONE }?.value,
    fatsNorm = fatsNorm.takeIf { it != FatsModel.NONE }?.value,
    carbohydratesNorm = carbohydratesNorm.takeIf { it != CarbohydratesModel.NONE }?.value,
    caloriesFact = caloriesFact.takeIf { it != CaloriesModel.NONE }?.value,
    proteinsFact = proteinsFact.takeIf { it != ProteinsModel.NONE }?.value,
    fatsFact = fatsFact.takeIf { it != FatsModel.NONE }?.value,
    carbohydratesFact = carbohydratesFact.takeIf { it != CarbohydratesModel.NONE }?.value,
    meals = meals.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    rationId = rationId.takeIf { it != RationIdModel.NONE }?.asString(),
    permissions = permissions.takeIf { it.isNotEmpty() }?.map { Permissions.valueOf(it.name) }?.toSet(),
)

fun BeMeal.toTransport() = Meal(
    mealName = Meal.MealName.valueOf(mealName.name),
    products = usedProducts.map { it.toTransport() }
)

fun BeUsedProduct.toTransport() = UsedProduct(
    authorId = authorId.takeIf { it != AuthorIdModel.NONE }?.asString(),
    category = category,
    productName = productName,
    caloriesPerHundredGrams = caloriesPerHundredGrams.takeIf { it != CaloriesModel.NONE }?.value,
    proteinsPerHundredGrams = proteinsPerHundredGrams.takeIf { it != ProteinsModel.NONE }?.value,
    fatsPerHundredGrams = fatsPerHundredGrams.takeIf { it != FatsModel.NONE }?.value,
    carbohydratesPerHundredGrams = carbohydratesPerHundredGrams.takeIf { it != CarbohydratesModel.NONE }?.value,
    productId = productId.takeIf { it != ProductIdModel.NONE }?.asString(),
    weight = weight,
    caloriesFact = caloriesFact.takeIf { it != CaloriesModel.NONE }?.value,
    proteinsFact = proteinsFact.takeIf { it != ProteinsModel.NONE }?.value,
    fatsFact = fatsFact.takeIf { it != FatsModel.NONE }?.value,
    carbohydratesFact = carbohydratesFact.takeIf { it != CarbohydratesModel.NONE }?.value,
    parentProductId = parentProductId.takeIf { it != ProductIdModel.NONE }?.asString()
)
