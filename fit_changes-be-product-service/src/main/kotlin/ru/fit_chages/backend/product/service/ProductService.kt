package ru.fit_chages.backend.product.service

import ru.fitChanges.backend.mapping.product.setQuery
import ru.fitChanges.backend.mapping.product.toCreateProductResponse
import ru.fitChanges.openapi.models.CreateProductRequest
import ru.fitChanges.openapi.models.CreateProductResponse
import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.product.logics.ProductCrud

class ProductService(
    private var crud: ProductCrud
) {
    suspend fun createProduct(context: BeContext, request: CreateProductRequest): CreateProductResponse {
        context.setQuery(request)
        context.responseProduct = context.requestProduct
        return context.toCreateProductResponse()
    }
}