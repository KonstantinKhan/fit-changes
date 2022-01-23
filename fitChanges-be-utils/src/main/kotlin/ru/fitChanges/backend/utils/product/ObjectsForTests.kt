package ru.fitChanges.backend.utils.product

import ru.fitChanges.openapi.models.CreatableProduct

val BEEF_FILLED = CreatableProduct(
    productName = "Говядина",
    caloriesPerHundredGrams = 187.0,
    proteinsPerHundredGrams = 18.9,
    fatsPerHundredGrams = 12.4,
    carbohydratePerHundredGrams = 0.0
)

val BEEF_NOT_FILLED = CreatableProduct(
    productName = null,
    caloriesPerHundredGrams = null,
    proteinsPerHundredGrams = null,
    fatsPerHundredGrams = null,
    carbohydratePerHundredGrams = null
)

const val REQUEST_ID_0001 = "rID:0001"