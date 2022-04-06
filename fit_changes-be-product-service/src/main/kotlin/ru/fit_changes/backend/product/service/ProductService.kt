package ru.fit_changes.backend.product.service

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.mapping.product.*
import ru.fit_changes.backend.product.logics.ProductCrud
import ru.fit_changes.openapi.models.*

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
    suspend fun readProduct(context: BeContext, request: ReadProductRequest): ReadProductResponse {
        crud.read(context.setQuery(request))
        return context.toReadProductResponse()
    }
    suspend fun updateProduct(context: BeContext, request: UpdateProductRequest): UpdateProductResponse {
        crud.update(context.setQuery(request))
        return context.toUpdateProductResponse()
    }
    suspend fun deleteProduct(context: BeContext, request: DeleteProductRequest): DeleteProductResponse {
        crud.delete(context.setQuery(request))
        return context.toDeleteProductResponse()
    }
    suspend fun searchProduct(context: BeContext, request: SearchProductRequest): SearchProductResponse {
        crud.search(context.setQuery(request))
        return context.toSearchProductResponse()
    }

}