package ru.fit_changes.backend.common.product.models

data class ProductModel(
    var productName: String = "",
    var caloriesPerHundredGrams: Double = 0.0,
    var proteinsPerHundredGrams: Double = 0.0,
    var fatsPerHundredGrams: Double = 0.0,
    var carbohydratesPerHundredGrams: Double = 0.0,
    var productId: ProductIdModel = ProductIdModel.NONE,
    var permissions: MutableSet<ProductPermissions> = mutableSetOf()
)
