package ru.fit_changes.backend.common.models.ration

import ru.fit_changes.backend.common.models.*
import ru.fit_changes.backend.common.product.models.ProductIdModel

data class BeUsedProduct(
    val authorId: AuthorIdModel,
    val category: String,
    val productName: String,
    val caloriesPerHundredGrams: CaloriesModel,
    val proteinsPerHundredGrams: ProteinsModel,
    val fatsPerHundredGrams: FatsModel,
    val carbohydratesPerHundredGrams: CarbohydratesModel,
    // todo Есть мнение, что id для продуктов внутри рациона вовсе не нужен
    val productId: ProductIdModel,
    val weight: Double,
    val caloriesFact: CaloriesModel,
    val proteinsFact: ProteinsModel,
    val fatsFact: FatsModel,
    val carbohydratesFact: CarbohydratesModel,
    val parentProductId: ProductIdModel
)
