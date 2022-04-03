package ru.fit_changes.backend.product.logics

import kotlinx.coroutines.runBlocking
import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.utils.product.BEEF_FILLED_MODEL
import ru.fit_changes.backend.utils.product.REQUEST_ID_0001
import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.backend.common.models.StubCases
import ru.fit_changes.backend.common.product.models.ProductIdModel
import kotlin.test.Test
import kotlin.test.assertEquals

class ProductCrudTest {

    @Test
    fun productCreateSuccess() {
        val crud = ProductCrud()
        val context = BeContext(
            requestId = REQUEST_ID_0001,
            requestProduct = BEEF_FILLED_MODEL,
            operation = Operations.CREATE,
            stubCase = StubCases.SUCCESS
        )
        runBlocking {
            crud.create(context)
            val expected = BEEF_FILLED_MODEL
            with(context.responseProduct) {
                assertEquals(expected.productName, productName)
                assertEquals(expected.caloriesPerHundredGrams, caloriesPerHundredGrams)
                assertEquals(expected.proteinsPerHundredGrams, proteinsPerHundredGrams)
                assertEquals(expected.fatsPerHundredGrams, fatsPerHundredGrams)
                assertEquals(expected.carbohydratesPerHundredGrams, carbohydratesPerHundredGrams)
                assertEquals(expected.productId, productId)
            }
        }
    }

    @Test
    fun productReadSuccess() {
        val crud = ProductCrud()
        val testProductId = "111"
        val context = BeContext(
            requestProductId = ProductIdModel(testProductId),
            operation = Operations.READ,
            stubCase = StubCases.SUCCESS
        )
        runBlocking {
            crud.read(context)
            val expected = BEEF_FILLED_MODEL.apply {
                productId = ProductIdModel(testProductId)
            }
            with(context.responseProduct) {
                assertEquals(expected.productId, productId)
            }
        }
    }
}