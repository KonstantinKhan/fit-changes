package ru.fitChanges.backend.mapping.product

import ru.fitChanges.openapi.models.*
import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.product.models.ProductModel
import ru.fit_changes.backend.common.models.StubCases
import ru.fit_changes.backend.common.product.models.ProductIdModel

fun BeContext.setQuery(query: CreateProductRequest) = apply {
    requestId = query.requestId ?: ""
    requestProduct = query.createProduct?.toModel(this) ?: ProductModel()
    stubCase = query.debug?.stubCase.toModel()
}

fun BeContext.setQuery(query: ReadProductRequest) = apply {
    requestId = query.requestId ?: ""
    requestProductId = ProductIdModel(query.readProductId ?: "")
    stubCase = query.debug?.stubCase.toModel()
}

fun BeContext.setQuery(query: UpdateProductRequest) = apply {
    requestId = query.requestId ?: ""
    requestProduct = query.updateProduct?.toModel(this) ?: ProductModel()
    stubCase = query.debug?.stubCase.toModel()
}

fun BeContext.setQuery(query: DeleteProductRequest) = apply {
    requestId = query.requestId ?: ""
    requestProductId = ProductIdModel(query.deleteProductId ?: "")
    stubCase = query.debug?.stubCase.toModel()
}

fun BeContext.setQuery(query: SearchProductRequest) = apply {
    requestId = query.requestId ?: ""
    requestQuery = query.query ?: ""
    stubCase = query.debug?.stubCase.toModel()
}

private fun CreatableProduct.toModel(context: BeContext) = ProductModel(
    productName = productName ?: "",
    caloriesPerHundredGrams = caloriesPerHundredGrams.validationProductParameters(context, "Calories"),
    proteinsPerHundredGrams = proteinsPerHundredGrams.validationProductParameters(context, "Proteins"),
    fatsPerHundredGrams = fatsPerHundredGrams.validationProductParameters(context, "Fats"),
    carbohydratesPerHundredGrams = carbohydratesPerHundredGrams.validationProductParameters(context, "Carbohydrates")
)

private fun UpdatableProduct.toModel(context: BeContext) = ProductModel(
    productName = productName ?: "",
    caloriesPerHundredGrams = caloriesPerHundredGrams.validationProductParameters(context, "Calories"),
    proteinsPerHundredGrams = proteinsPerHundredGrams.validationProductParameters(context, "Proteins"),
    fatsPerHundredGrams = fatsPerHundredGrams.validationProductParameters(context, "Fats"),
    carbohydratesPerHundredGrams = carbohydratesPerHundredGrams.validationProductParameters(context, "Carbohydrates"),
    productId = ProductIdModel(productId ?: ""),
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