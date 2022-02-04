package ru.fit_changes.backend.app.ktor.product.plugins

import io.ktor.application.*
import io.ktor.routing.*
import ru.fit_chages.backend.product.service.ProductService
import ru.fit_changes.backend.app.ktor.product.productRouting

fun Application.configRouting(
    productService: ProductService
) {
    routing {
        productRouting(productService)
    }
}