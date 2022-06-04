package ru.fit_changes.backend.app.ktor.product.controllers

import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import ru.fit_changes.backend.app.ktor.product.configs.KtorAuthConfig
import ru.fit_changes.backend.utils.product.BEEF_FILLED_CREATABLE_PRODUCT
import ru.fit_changes.backend.utils.product.REQUEST_ID_0001
import ru.fit_changes.openapi.models.BaseDebugRequest
import ru.fit_changes.openapi.models.CreateProductRequest
import ru.fit_changes.openapi.models.CreateProductResponse
import kotlin.test.Test

class ProductAuthTest : RouterTest() {

    @Test
    fun authWrongIssuerTest() {
        val request = CreateProductRequest(
            requestId = REQUEST_ID_0001,
            createProduct = BEEF_FILLED_CREATABLE_PRODUCT,
            debug = BaseDebugRequest(
                mode = BaseDebugRequest.Mode.TEST,
                stubCase = BaseDebugRequest.StubCase.SUCCESS
            )
        )
        testPostRequest<CreateProductResponse>(
            body = request,
            uri = "product/create",
            authConfig = KtorAuthConfig.TEST.copy(
                issuer = "other"
            ),
            result = Unauthorized
        )
    }

    @Test
    fun authExpiredTokenTest() {
        val request = CreateProductRequest(
            requestId = REQUEST_ID_0001,
            createProduct = BEEF_FILLED_CREATABLE_PRODUCT,
            debug = BaseDebugRequest(
                mode = BaseDebugRequest.Mode.TEST,
                stubCase = BaseDebugRequest.StubCase.SUCCESS
            )
        )
        testPostRequest<CreateProductResponse>(
            body = request,
            uri = "product/create",
            yearExpiration = 2022,
            result = Unauthorized
        )
    }

    @Test
    fun authWrongSecretTest() {
        val request = CreateProductRequest(
            requestId = REQUEST_ID_0001,
            createProduct = BEEF_FILLED_CREATABLE_PRODUCT,
            debug = BaseDebugRequest(
                mode = BaseDebugRequest.Mode.TEST,
                stubCase = BaseDebugRequest.StubCase.SUCCESS
            )
        )
        testPostRequest<CreateProductResponse>(
            body = request,
            uri = "product/create",
            authConfig = KtorAuthConfig.TEST.copy(),
            result = Unauthorized
        )
    }

    @Test
    fun authWrongAudienceTest() {
        val request = CreateProductRequest(
            requestId = REQUEST_ID_0001,
            createProduct = BEEF_FILLED_CREATABLE_PRODUCT,
            debug = BaseDebugRequest(
                mode = BaseDebugRequest.Mode.TEST,
                stubCase = BaseDebugRequest.StubCase.SUCCESS
            )
        )
        testPostRequest<CreateProductResponse>(
            body = request,
            uri = "product/create",
            authConfig = KtorAuthConfig.TEST.copy(
                audience = "other"
            ),
            result = Unauthorized
        )
    }

    @Test
    fun authEmptyGroupTest() {
        val request = CreateProductRequest(
            requestId = REQUEST_ID_0001,
            createProduct = BEEF_FILLED_CREATABLE_PRODUCT,
            debug = BaseDebugRequest(
                mode = BaseDebugRequest.Mode.TEST,
                stubCase = BaseDebugRequest.StubCase.SUCCESS
            )
        )
        testPostRequest<CreateProductResponse>(
            body = request,
            uri = "product/create",
            groups = arrayOf(),
            result = Unauthorized
        )
    }
}