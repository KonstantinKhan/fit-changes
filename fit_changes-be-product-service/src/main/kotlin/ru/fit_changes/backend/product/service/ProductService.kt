package ru.fit_changes.backend.product.service

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.mapping.product.setQuery
import ru.fit_changes.backend.mapping.product.toCreateProductResponse
import ru.fit_changes.backend.product.logics.ProductCrud
import ru.fit_changes.openapi.models.BaseMessage
import ru.fit_changes.openapi.models.CreateProductRequest
import ru.fit_changes.openapi.models.CreateProductResponse

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
        crud.create(context.setQuery(request))
        return context.toCreateProductResponse()
    }
}