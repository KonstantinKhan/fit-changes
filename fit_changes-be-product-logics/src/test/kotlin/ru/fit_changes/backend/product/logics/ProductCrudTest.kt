package ru.fit_changes.backend.product.logics

import kotlinx.coroutines.runBlocking
import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.utils.product.BEEF_FILLED_MODEL
import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.backend.common.models.StubCases
import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.backend.utils.product.PRODUCT_ID_0001
import ru.fit_changes.backend.utils.product.PRODUCT_ID_0002
import kotlin.test.Test
import kotlin.test.assertEquals

class ProductCrudTest {

    @Test
    fun productCreateSuccess() {
        val crud = ProductCrud()
        val context = BeContext(
            requestProduct = BEEF_FILLED_MODEL,
            operation = Operations.CREATE,
            stubCase = StubCases.SUCCESS
        )
        runBlocking {
            crud.create(context)
            val expected = BEEF_FILLED_MODEL.apply {
                productId = ProductIdModel(PRODUCT_ID_0001)
            }
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
        val context = BeContext(
            requestProductId = ProductIdModel(PRODUCT_ID_0002),
            operation = Operations.READ,
            stubCase = StubCases.SUCCESS
        )
        runBlocking {
            crud.read(context)
            val expected = BEEF_FILLED_MODEL.apply {
                productId = ProductIdModel(PRODUCT_ID_0002)
            }
            with(context.responseProduct) {
                assertEquals(expected.productId, productId)
            }
        }
    }
}