package ru.fit_changes.backend.mapping.product

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.backend.common.models.*
import ru.fit_changes.backend.common.product.models.*
import ru.fit_changes.openapi.models.*

fun BeContext.setQuery(query: CreateProductRequest) = apply {
    operation = Operations.CREATE
    requestId = query.requestId ?: ""
    requestProduct = query.createProduct?.toModel() ?: ProductModel()
    stubCase = query.debug?.stubCase.toModel()
    workMode = query.debug?.mode.toModel()
}

fun BeContext.setQuery(query: ReadProductRequest) = apply {
    operation = Operations.READ
    requestId = query.requestId ?: ""
    requestProductId = ProductIdModel(query.readProductId ?: "")
    stubCase = query.debug?.stubCase.toModel()
    workMode = query.debug?.mode.toModel()
}

fun BeContext.setQuery(query: UpdateProductRequest) = apply {
    operation = Operations.UPDATE
    requestId = query.requestId ?: ""
    requestProduct = query.updateProduct?.toModel() ?: ProductModel()
    stubCase = query.debug?.stubCase.toModel()
    workMode = query.debug?.mode.toModel()
}

fun BeContext.setQuery(query: DeleteProductRequest) = apply {
    operation = Operations.DELETE
    requestId = query.requestId ?: ""
    requestProductId = ProductIdModel(query.deleteProductId ?: "")
    stubCase = query.debug?.stubCase.toModel()
    workMode = query.debug?.mode.toModel()
}

fun BeContext.setQuery(query: SearchProductRequest) = apply {
    operation = Operations.SEARCH
    requestId = query.requestId ?: ""
    requestProductFilter = query.query?.let { ProductSearchFilter(searchStr = it) } ?: ProductSearchFilter()
    stubCase = query.debug?.stubCase.toModel()
    workMode = query.debug?.mode.toModel()
}

private fun CreatableProduct.toModel() = ProductModel(
    productName = productName ?: "",
    caloriesPerHundredGrams = caloriesPerHundredGrams?.let { CaloriesModel(it) } ?: CaloriesModel.NONE,
    proteinsPerHundredGrams = proteinsPerHundredGrams?.let { ProteinsModel(it) } ?: ProteinsModel.NONE,
    fatsPerHundredGrams = fatsPerHundredGrams?.let { FatsModel(it) } ?: FatsModel.NONE,
    carbohydratesPerHundredGrams = carbohydratesPerHundredGrams?.let { CarbohydratesModel(it) }
        ?: CarbohydratesModel.NONE
)

private fun UpdatableProduct.toModel() = ProductModel(
    productName = productName ?: "",
    caloriesPerHundredGrams = caloriesPerHundredGrams?.let { CaloriesModel(it) } ?: CaloriesModel.NONE,
    proteinsPerHundredGrams = proteinsPerHundredGrams?.let { ProteinsModel(it) } ?: ProteinsModel.NONE,
    fatsPerHundredGrams = fatsPerHundredGrams?.let { FatsModel(it) } ?: FatsModel.NONE,
    carbohydratesPerHundredGrams = carbohydratesPerHundredGrams?.let { CarbohydratesModel(it) }
        ?: CarbohydratesModel.NONE,
    productId = ProductIdModel(productId ?: ""),
)

private fun BaseDebugRequest.StubCase?.toModel(): StubCases = when (this) {
    BaseDebugRequest.StubCase.SUCCESS -> StubCases.SUCCESS
    BaseDebugRequest.StubCase.DATABASE_ERROR -> StubCases.DATABASE_ERROR
    null -> StubCases.NONE
}

private fun BaseDebugRequest.Mode?.toModel(): WorkMode = when (this) {
    BaseDebugRequest.Mode.TEST -> WorkMode.TEST
    BaseDebugRequest.Mode.STUB -> WorkMode.STUB
    else -> WorkMode.PROD
}