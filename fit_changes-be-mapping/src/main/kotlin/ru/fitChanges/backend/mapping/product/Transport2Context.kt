package ru.fitChanges.backend.mapping.product

import ru.fitChanges.openapi.models.BaseDebugRequest
import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.product.models.ProductModel
import ru.fitChanges.openapi.models.CreatableProduct
import ru.fitChanges.openapi.models.CreateProductRequest
import ru.fit_changes.backend.common.models.StubCases

fun BeContext.setQuery(query: CreateProductRequest) = apply {
    requestId = query.requestId ?: ""
    requestProduct = query.createProduct?.toModel(this) ?: ProductModel()
    stubCase = query.debug?.stubCase.toModel()
}

private fun CreatableProduct.toModel(context: BeContext) = ProductModel(
    productName = productName ?: "",
    caloriesPerHundredGrams = caloriesPerHundredGrams.validationProductParameters(context, "Calories"),
    proteinsPerHundredGrams = proteinsPerHundredGrams.validationProductParameters(context, "Proteins"),
    fatsPerHundredGrams = fatsPerHundredGrams.validationProductParameters(context, "Fats"),
    carbohydratesPerHundredGrams = carbohydratesPerHundredGrams.validationProductParameters(context, "Carbohydrates")
)

private fun BaseDebugRequest.StubCase?.toModel(): StubCases = when (this) {
    BaseDebugRequest.StubCase.SUCCESS -> StubCases.SUCCESS
    BaseDebugRequest.StubCase.DATABASE_ERROR -> StubCases.DATABASE_ERROR
    null -> StubCases.NONE
}

private fun Double?.validationProductParameters(context: BeContext, parameter: String) =
    if (this != null) {
        this
    } else {
        context.addError(parameter, "$parameter cannot be null")
        0.0
    }