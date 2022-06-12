package ru.fit_changes.backend.repo.test

import ru.fit_changes.backend.common.models.*
import ru.fit_changes.backend.common.product.models.*
import java.util.*

abstract class BaseInitProduct : IInitObjects<ProductModel> {
    fun createInitTestModel(
        suf: String,
        productName: String = "Chicken",
    ) = ProductModel(
        productId = ProductIdModel(UUID.randomUUID()),
        authorId = AuthorIdModel(UUID.randomUUID()),
        productName = "$suf $productName",
        caloriesPerHundredGrams = CaloriesModel(calories = 110.0),
        proteinsPerHundredGrams = ProteinsModel(proteins = 21.0),
        fatsPerHundredGrams = FatsModel(fats = 3.0),
        carbohydratesPerHundredGrams = CarbohydratesModel(carbohydrates = 0.0),
    )
}