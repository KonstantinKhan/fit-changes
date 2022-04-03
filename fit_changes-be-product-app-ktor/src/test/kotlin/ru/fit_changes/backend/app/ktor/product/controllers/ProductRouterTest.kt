package ru.fit_changes.backend.app.ktor.product.controllers

import ru.fit_changes.backend.utils.product.BEEF_FILLED_CREATABLE_PRODUCT
import ru.fit_changes.backend.utils.product.REQUEST_ID_0001
import ru.fit_changes.openapi.models.BaseDebugRequest
import ru.fit_changes.openapi.models.CreateProductRequest
import ru.fit_changes.openapi.models.CreateProductResponse
import kotlin.test.Test

class ProductRouterTest : RouterTest() {

    @Test
    fun testPostProductCreate() {
        val data = CreateProductRequest(
            requestId = REQUEST_ID_0001,
            createProduct = BEEF_FILLED_CREATABLE_PRODUCT,
            debug = BaseDebugRequest(mode = BaseDebugRequest.Mode.TEST, stubCase = BaseDebugRequest.StubCase.SUCCESS)
        )

        testPostRequest<CreateProductResponse>(data, "product/create") {

        }
    }
}