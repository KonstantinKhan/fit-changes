package ru.fit_changes.backend.utils.product

import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.backend.common.product.models.ProductModel
import ru.fit_changes.backend.common.product.models.ProductPermissions
import ru.fitChanges.openapi.models.CreatableProduct

const val REQUEST_ID_0001 = "requestID:0001"
const val PRODUCT_ID_0001 = "productID:0001"

val BEEF_FILLED_CREATABLE_PRODUCT = CreatableProduct(
    productName = "Говядина",
    caloriesPerHundredGrams = 187.0,
    proteinsPerHundredGrams = 18.9,
    fatsPerHundredGrams = 12.4,
    carbohydratesPerHundredGrams = 0.0
)

val BEEF_NOT_FILLED_CREATABLE_PRODUCT = CreatableProduct(
    productName = null,
    caloriesPerHundredGrams = null,
    proteinsPerHundredGrams = null,
    fatsPerHundredGrams = null,
    carbohydratesPerHundredGrams = null
)

val BEEF_FILLED_MODEL = ProductModel(
    productName = "Говядина",
    caloriesPerHundredGrams = 187.0,
    proteinsPerHundredGrams = 18.9,
    fatsPerHundredGrams = 12.4,
    carbohydratesPerHundredGrams = 0.0,
    productId = ProductIdModel(PRODUCT_ID_0001),
    permissions = mutableSetOf(ProductPermissions.CREATE)
)

val BEEF_FILLED_MODEL_WITHOUT_PRODUCT_ID =
    BEEF_FILLED_MODEL.copy(
        productId = ProductIdModel.NONE,
        permissions = mutableSetOf()
    )
