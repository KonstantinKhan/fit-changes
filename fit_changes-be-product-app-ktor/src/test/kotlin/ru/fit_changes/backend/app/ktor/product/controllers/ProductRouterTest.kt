package ru.fit_changes.backend.app.ktor.product.controllers

import ru.fit_changes.backend.utils.product.*
import ru.fit_changes.openapi.models.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ProductRouterTest : RouterTest() {
    @Test
    fun testPostProductCreateSuccess() {
        val request = CreateProductRequest(
            requestId = REQUEST_ID_0001,
            createProduct = BEEF_FILLED_CREATABLE_PRODUCT,
            debug = BaseDebugRequest(
                mode = BaseDebugRequest.Mode.TEST,
                stubCase = BaseDebugRequest.StubCase.SUCCESS
            )
        )
        testPostRequest<CreateProductResponse>(request, "product/create") {
            assertNull(errors)
            assertEquals(CreateProductResponse.Result.SUCCESS, result)
            assertEquals(request.requestId, requestId)
            assertEquals(
                BEEF_FILLED_RESPONSE.copy(
                    productId = createdProduct?.productId,
                    authorId = createdProduct?.authorId
                ), createdProduct
            )
        }
    }

    @Test
    fun testPostProductReadSuccess() {
        val request = ReadProductRequest(
            requestId = REQUEST_ID_0001,
            readProductId = PRODUCT_ID_0001,
            debug = BaseDebugRequest(
                mode = BaseDebugRequest.Mode.TEST,
                stubCase = BaseDebugRequest.StubCase.SUCCESS
            )
        )
        testPostRequest<ReadProductResponse>(request, "product/read") {
            assertNull(errors)
            assertEquals(ReadProductResponse.Result.SUCCESS, result)
            assertEquals(request.requestId, requestId)
            assertEquals(
                BEEF_FILLED_RESPONSE.copy(
                    productId = readProduct?.productId
                ), readProduct
            )
        }
    }

    @Test
    fun testPostProductUpdateSuccess() {
        val request = UpdateProductRequest(
            requestId = REQUEST_ID_0001,
            updateProduct = BEEF_FILLED_UPDATABLE_PRODUCT.copy(
                productId = PRODUCT_ID_0002
            ),
            debug = BaseDebugRequest(
                mode = BaseDebugRequest.Mode.TEST,
                stubCase = BaseDebugRequest.StubCase.SUCCESS
            )
        )
        testPostRequest<UpdateProductResponse>(request, "product/update") {
            assertNull(errors)
            assertEquals(UpdateProductResponse.Result.SUCCESS, result)
            assertEquals(request.requestId, requestId)
            assertEquals(
                BEEF_FILLED_RESPONSE.copy(
                    productId = updateProduct?.productId
                ), updateProduct
            )
        }
    }

    @Test
    fun testPostProductDeleteSuccess() {
        val request = DeleteProductRequest(
            requestId = REQUEST_ID_0001,
            deleteProductId = PRODUCT_ID_0001,
            debug = BaseDebugRequest(
                mode = BaseDebugRequest.Mode.TEST,
                stubCase = BaseDebugRequest.StubCase.SUCCESS
            )
        )
        testPostRequest<DeleteProductResponse>(request, "product/delete") {
            assertNull(errors)
            assertEquals(DeleteProductResponse.Result.SUCCESS, result)
            assertEquals(request.requestId, requestId)
            assertEquals(
                BEEF_FILLED_RESPONSE.copy(
                    productId = deletedProduct?.productId
                ), deletedProduct
            )
        }
    }

    @Test
    fun testPostProductSearchSuccess() {
        val request = SearchProductRequest(
            requestId = REQUEST_ID_0001,
            query = "гов",
            debug = BaseDebugRequest(
                mode = BaseDebugRequest.Mode.TEST,
                stubCase = BaseDebugRequest.StubCase.SUCCESS
            )
        )
        testPostRequest<SearchProductResponse>(request, "product/search") {
            assertNull(errors)
            assertEquals(SearchProductResponse.Result.SUCCESS, result)
            assertEquals(request.requestId, requestId)
            assertEquals(
                listOf(
                    BEEF_FILLED_RESPONSE.copy(productId = PRODUCT_ID_0001),
                    BEEF_FILLED_RESPONSE.copy(productId = PRODUCT_ID_0002)
                ), foundProducts
            )
        }
    }
}