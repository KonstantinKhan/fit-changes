package ru.fit_changes.backend.ration.repo.inmemory

import ru.fit_changes.backend.common.models.*
import ru.fit_changes.backend.common.models.ration.BeUsedProduct
import ru.fit_changes.backend.common.product.models.ProductIdModel

data class RepoInMemoryUsedProduct(
    val authorId: String? = null,
    val category: String? = null,
    val productName: String? = null,
    val caloriesPerHundredGrams: Double? = null,
    val proteinsPerHundredGrams: Double? = null,
    val fatsPerHundredGrams: Double? = null,
    val carbohydratesPerHundredGrams: Double? = null,
    val productId: String? = null,
    val weight: Double? = null,
    val caloriesFact: Double? = null,
    val proteinsFact: Double? = null,
    val fatsFact: Double? = null,
    val carbohydratesFact: Double? = null,
    val parentProductId: String? = null
) {
    constructor(internal: BeUsedProduct) : this(
        authorId = internal.authorId.takeIf { it != AuthorIdModel.NONE }?.asString(),
        category = internal.category,
        productName = internal.productName,
        caloriesPerHundredGrams = internal.caloriesPerHundredGrams.takeIf { it != CaloriesModel.NONE }?.value,
        proteinsPerHundredGrams = internal.proteinsPerHundredGrams.takeIf { it != ProteinsModel.NONE }?.value,
        fatsPerHundredGrams = internal.fatsPerHundredGrams.takeIf { it != FatsModel.NONE }?.value,
        carbohydratesPerHundredGrams = internal.carbohydratesPerHundredGrams.takeIf { it != CarbohydratesModel.NONE }?.value,
        productId = internal.productId.takeIf { it != ProductIdModel.NONE }?.asString(),
        weight = internal.weight,
        caloriesFact = internal.caloriesFact.takeIf { it != CaloriesModel.NONE }?.value,
        proteinsFact = internal.proteinsFact.takeIf { it != ProteinsModel.NONE }?.value,
        fatsFact = internal.fatsFact.takeIf { it != FatsModel.NONE }?.value,
        carbohydratesFact = internal.carbohydratesFact.takeIf { it != CarbohydratesModel.NONE }?.value,
        parentProductId = internal.parentProductId.takeIf { it != ProductIdModel.NONE }?.asString()
    )

    fun toInternal(): BeUsedProduct = BeUsedProduct(
        authorId = authorId?.let { AuthorIdModel(it) } ?: AuthorIdModel.NONE,
        category = category ?: "",
        productName = productName ?: "",
        caloriesPerHundredGrams = caloriesPerHundredGrams?.let { CaloriesModel(it) } ?: CaloriesModel.NONE,
        proteinsPerHundredGrams = proteinsPerHundredGrams?.let { ProteinsModel(it) } ?: ProteinsModel.NONE,
        fatsPerHundredGrams = fatsPerHundredGrams?.let { FatsModel(it) } ?: FatsModel.NONE,
        carbohydratesPerHundredGrams = carbohydratesPerHundredGrams?.let { CarbohydratesModel(it) }
            ?: CarbohydratesModel.NONE,
        productId = productId?.let { ProductIdModel(it) } ?: ProductIdModel.NONE,
        weight = weight ?: 0.0,
        caloriesFact = caloriesFact?.let { CaloriesModel(it) } ?: CaloriesModel.NONE,
        proteinsFact = proteinsFact?.let { ProteinsModel(it) } ?: ProteinsModel.NONE,
        fatsFact = fatsFact?.let { FatsModel(it) } ?: FatsModel.NONE,
        carbohydratesFact = carbohydratesFact?.let { CarbohydratesModel(it) } ?: CarbohydratesModel.NONE,
        parentProductId = parentProductId?.let { ProductIdModel(it) } ?: ProductIdModel.NONE

    )
}
