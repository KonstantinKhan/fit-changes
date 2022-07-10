package ru.fit_changes.backend.product.logics

import kotlinx.coroutines.runBlocking
import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.utils.product.BEEF_FILLED_MODEL
import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.backend.common.models.enums.StubCases
import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.backend.common.product.models.ProductSearchFilter
import ru.fit_changes.backend.utils.product.PRODUCT_ID_0001
import ru.fit_changes.backend.utils.product.PRODUCT_ID_0002
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProductCrudTest {

    @Test
    fun productCreateSuccess() {
        val crud = ProductCrud()
        val context = BeContext(
            operation = Operations.CREATE,
            stubCase = StubCases.SUCCESS,
            requestProduct = BEEF_FILLED_MODEL,
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
    fun productCreateFailing() {
        val crud = ProductCrud()
        val context = BeContext(
            operation = Operations.CREATE,
            stubCase = StubCases.DATABASE_ERROR,
            requestProduct = BEEF_FILLED_MODEL,
        )
        runBlocking {
            crud.create(context)
            assertTrue(context.errors.isNotEmpty())
        }
    }

    @Test
    fun productReadSuccess() {
        val crud = ProductCrud()
        val context = BeContext(
            operation = Operations.READ,
            stubCase = StubCases.SUCCESS,
            requestProductId = ProductIdModel(PRODUCT_ID_0002),
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

    @Test
    fun productReadFailing() {
        val crud = ProductCrud()
        val context = BeContext(
            operation = Operations.CREATE,
            stubCase = StubCases.DATABASE_ERROR,
        )
        runBlocking {
            crud.read(context)
            assertTrue(context.errors.isNotEmpty())
        }
    }

    @Test
    fun productUpdateSuccess() {
        val crud = ProductCrud()
        val context = BeContext(
            operation = Operations.UPDATE,
            stubCase = StubCases.SUCCESS,
            requestProduct = BEEF_FILLED_MODEL,
        )
        runBlocking {
            crud.update(context)
            val expected = BEEF_FILLED_MODEL
            with(context.responseProduct) {
                assertEquals(expected.productId, productId)
                assertEquals(expected.productName, productName)
                assertEquals(expected.caloriesPerHundredGrams, caloriesPerHundredGrams)
                assertEquals(expected.proteinsPerHundredGrams, proteinsPerHundredGrams)
                assertEquals(expected.fatsPerHundredGrams, fatsPerHundredGrams)
                assertEquals(expected.carbohydratesPerHundredGrams, carbohydratesPerHundredGrams)
            }
        }
    }

    @Test
    fun productUpdateFailing() {
        val crud = ProductCrud()
        val context = BeContext(
            operation = Operations.UPDATE,
            stubCase = StubCases.DATABASE_ERROR,
        )
        runBlocking {
            crud.update(context)
            assertTrue(context.errors.isNotEmpty())
        }
    }

    @Test
    fun productDeleteSuccess() {
        val crud = ProductCrud()
        val context = BeContext(
            operation = Operations.DELETE,
            stubCase = StubCases.SUCCESS,
            requestProductId = ProductIdModel(PRODUCT_ID_0002),
        )
        runBlocking {
            crud.delete(context)
            val expected = BEEF_FILLED_MODEL.apply { productId = ProductIdModel(PRODUCT_ID_0002) }
            with(context.responseProduct) {
                assertEquals(expected.productId, productId)
                assertEquals(expected.productName, productName)
                assertEquals(expected.caloriesPerHundredGrams, caloriesPerHundredGrams)
                assertEquals(expected.proteinsPerHundredGrams, proteinsPerHundredGrams)
                assertEquals(expected.fatsPerHundredGrams, fatsPerHundredGrams)
                assertEquals(expected.carbohydratesPerHundredGrams, carbohydratesPerHundredGrams)
            }
        }
    }

    @Test
    fun productDeleteFailings() {
        val crud = ProductCrud()
        val context = BeContext(
            operation = Operations.DELETE,
            stubCase = StubCases.WRONG_ID,
        )
        runBlocking {
            crud.delete(context)
            assertTrue(context.errors.isNotEmpty())
        }
    }

    @Test
    fun productSearchSuccess() {
        val crud = ProductCrud()
        val context = BeContext(
            operation = Operations.SEARCH,
            stubCase = StubCases.SUCCESS,
            requestProductFilter = ProductSearchFilter(searchStr = "Помидор")
        )
        runBlocking {
            crud.search(context)
            assertTrue(context.foundProducts.size == 2)
        }
    }

    @Test
    fun productSearchFailing() {
        val crud = ProductCrud()
        val context = BeContext(
            operation = Operations.SEARCH,
            stubCase = StubCases.DATABASE_ERROR
        )
        runBlocking {
            crud.search(context)
            assertTrue(context.errors.isNotEmpty())
        }
    }
}