package ru.fit_changes.backend.common.product.models

data class ProductModel(
    var productId: ProductIdModel = ProductIdModel.NONE,
    var authorId: AuthorIdModel = AuthorIdModel.NONE,
    var productName: String = "",
    var caloriesPerHundredGrams: CaloriesModel = CaloriesModel.NONE,
    var proteinsPerHundredGrams: ProteinsModel = ProteinsModel.NONE,
    var fatsPerHundredGrams: FatsModel = FatsModel.NONE,
    var carbohydratesPerHundredGrams: CarbohydratesModel = CarbohydratesModel.NONE,
    var permissions: MutableSet<ProductPermissions> = mutableSetOf()
)
