package ru.fit_changes.backend.app.ktor.product.controllers

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.fit_chages.backend.product.service.ProductService
import ru.fit_changes.backend.common.context.BeContext

suspend fun ApplicationCall.createProduct(productService: ProductService) {
    respond(productService.createProduct(BeContext(), receive()))
}