package ru.fit_changes.backend.utils.product

import ru.fitChanges.openapi.models.CreatableProduct
import ru.fitChanges.openapi.models.UpdatableProduct
import ru.fit_changes.backend.common.product.models.*

const val REQUEST_ID_0001 = "requestID:0001"
const val PRODUCT_ID_0001 = "productID:0001"

val BEEF_FILLED_CREATABLE_PRODUCT = CreatableProduct(
    productName = "Говядина",
    caloriesPerHundredGrams = 187.0,
    proteinsPerHundredGrams = 18.9,
    fatsPerHundredGrams = 12.4,
    carbohydratesPerHundredGrams = 0.0
)

val BEEF_NOT_CALORIES = BEEF_FILLED_CREATABLE_PRODUCT.copy(
    caloriesPerHundredGrams = null
)

val BEEF_NOT_FILLED_CREATABLE_PRODUCT = CreatableProduct(
    productName = null,
    caloriesPerHundredGrams = null,
    proteinsPerHundredGrams = null,
    fatsPerHundredGrams = null,
    carbohydratesPerHundredGrams = null
)

val BEEF_FILLED_UPDATABLE_PRODUCT = UpdatableProduct(
    productName = "Говядина",
    caloriesPerHundredGrams = 187.0,
    proteinsPerHundredGrams = 18.9,
    fatsPerHundredGrams = 12.4,
    carbohydratesPerHundredGrams = 0.0,
    productId = PRODUCT_ID_0001
)

val BEEF_NOT_FILLED_UPDATABLE_PRODUCT = BEEF_FILLED_UPDATABLE_PRODUCT.copy(
    fatsPerHundredGrams = null
)

val BEEF_FILLED_MODEL = ProductModel(
    productId = ProductIdModel(PRODUCT_ID_0001),
    productName = "Говядина",
    caloriesPerHundredGrams = CaloriesModel(187.0),
    proteinsPerHundredGrams = ProteinsModel(18.9),
    fatsPerHundredGrams = FatsModel(12.4),
    carbohydratesPerHundredGrams = CarbohydratesModel(0.0),
)
