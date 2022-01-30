package ru.fitChanges.backend.mapping.product

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.product.models.ProductModel
import ru.fitChanges.openapi.models.CreatableProduct
import ru.fitChanges.openapi.models.CreateProductRequest

fun BeContext.setQuery(query: CreateProductRequest) = apply {
    requestId = query.requestId ?: ""
    requestProduct = query.createProduct?.toModel(this) ?: ProductModel()
}

private fun CreatableProduct.toModel(beContext: BeContext) = ProductModel(
    productName = productName ?: "",
    caloriesPerHundredGrams = caloriesPerHundredGrams.validationProductParameters(beContext, "Calories"),
    proteinsPerHundredGrams = proteinsPerHundredGrams.validationProductParameters(beContext, "Proteins"),
    fatsPerHundredGrams = fatsPerHundredGrams.validationProductParameters(beContext, "Fats"),
    carbohydratePerHundredGrams = carbohydratePerHundredGrams.validationProductParameters(beContext, "Carbohydrates")
)

private fun Double?.validationProductParameters(context: BeContext, parameter: String) =
    if (this != null) {
        this
    } else {
        context.addError("$parameter cannot be null")
        0.0
    }