package ru.fit_changes.backend.app.ktor.product.routes

import io.ktor.application.*
import io.ktor.routing.*
import ru.fit_chages.backend.product.service.ProductService
import ru.fit_changes.backend.app.ktor.product.controllers.createProduct

fun Route.productRouting(productService: ProductService) =
    route("product") {
        post("create") {
            call.createProduct(productService)
        }
        post("read") {

        }
        post("update") { }
        post("delete") { }
        post("search") { }
    }

fun Application.registerProductRoutes(
    productService: ProductService
) {
    routing {
        productRouting(productService)
    }
}
