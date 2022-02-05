package ru.fit_changes.backend.app.ktor.product.controllers

import io.ktor.application.*
import ru.fitChanges.openapi.models.CreateProductRequest
import ru.fitChanges.openapi.models.CreateProductResponse
import ru.fit_chages.backend.product.service.ProductService
import ru.fit_changes.backend.app.ktor.product.helpers.handleRoute

suspend fun ApplicationCall.createProduct(productService: ProductService) {
    handleRoute<CreateProductRequest, CreateProductResponse>() { request ->
        productService.createProduct(this, request)
    }
}