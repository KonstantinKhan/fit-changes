package common.models

data class ProductModel(
    var productName: String = "",
    var caloriesPerHundredGrams: Double = 0.0,
    var proteinsPerHundredGrams: Double = 0.0,
    var fatsPerHundredGrams: Double = 0.0,
    var carbohydratePerHundredGrams: Double = 0.0,
    var idProduct: ProductIdModel = ProductIdModel.NONE,
    var permissions: MutableSet<ProductPermissions> = mutableSetOf()
)
