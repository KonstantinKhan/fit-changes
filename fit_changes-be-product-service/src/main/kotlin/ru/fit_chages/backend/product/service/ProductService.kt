package ru.fit_chages.backend.product.service

import ru.fitChanges.backend.mapping.product.setQuery
import ru.fitChanges.backend.mapping.product.toCreateProductResponse
import ru.fitChanges.openapi.models.BaseMessage
import ru.fitChanges.openapi.models.CreateProductRequest
import ru.fitChanges.openapi.models.CreateProductResponse
import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.product.logics.ProductCrud

class ProductService(
    private var crud: ProductCrud
) {

    suspend fun handleRequest(context: BeContext, request: BaseMessage) = try {
        when (request) {
            is CreateProductRequest -> createProduct(context, request)
            else -> throw DataNotAllowedException("Request is not allowed", request)
        }
    } catch (t: Throwable) {
        println(t.message)
    }

    suspend fun createProduct(context: BeContext, request: CreateProductRequest): CreateProductResponse {
        context.setQuery(request)

        // temporary solution
        context.responseProduct = context.requestProduct

        return context.toCreateProductResponse()
    }
}