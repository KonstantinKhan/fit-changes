package ru.fitChanges.backend.utils.product

import ru.fitChanges.backend.common.product.models.ProductIdModel
import ru.fitChanges.backend.common.product.models.ProductModel
import ru.fitChanges.backend.common.product.models.ProductPermissions
import ru.fitChanges.openapi.models.CreatableProduct

const val REQUEST_ID_0001 = "requestID:0001"
const val PRODUCT_ID_0001 = "productID:0001"

val BEEF_FILLED_CREATABLE_PRODUCT = CreatableProduct(
    productName = "Говядина",
    caloriesPerHundredGrams = 187.0,
    proteinsPerHundredGrams = 18.9,
    fatsPerHundredGrams = 12.4,
    carbohydratePerHundredGrams = 0.0
)

val BEEF_NOT_FILLED_CREATABLE_PRODUCT = CreatableProduct(
    productName = null,
    caloriesPerHundredGrams = null,
    proteinsPerHundredGrams = null,
    fatsPerHundredGrams = null,
    carbohydratePerHundredGrams = null
)

val BEEF_FILLED_MODEL = ProductModel(
    productName = "Говядина",
    caloriesPerHundredGrams = 187.0,
    proteinsPerHundredGrams = 18.9,
    fatsPerHundredGrams = 12.4,
    carbohydratePerHundredGrams = 0.0,
    productId = ProductIdModel(PRODUCT_ID_0001),
    permissions = mutableSetOf(ProductPermissions.CREATE)
)
