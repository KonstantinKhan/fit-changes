import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.models.*
import ru.fit_changes.backend.common.product.models.*
import ru.fit_changes.backend.mapping.product.setQuery
import ru.fit_changes.backend.mapping.product.toCreateProductResponse
import ru.fit_changes.backend.mapping.product.toReadProductResponse
import ru.fit_changes.backend.mapping.product.toUpdateProductResponse
import ru.fit_changes.backend.utils.product.*
import ru.fit_changes.openapi.models.*
import kotlin.test.*

class MappingTest {

    @Test
    fun createBeefRequestSuccess() {
        val beContext = BeContext().setQuery(
            CreateProductRequest(
                requestId = REQUEST_ID_0001,
                createProduct = BEEF_FILLED_CREATABLE_PRODUCT
            )
        )

        assertEquals(REQUEST_ID_0001, beContext.requestId)
        assertEquals(BEEF_FILLED_CREATABLE_PRODUCT.productName, beContext.requestProduct.productName)
        assertEquals(
            BEEF_FILLED_CREATABLE_PRODUCT.caloriesPerHundredGrams?.let { CaloriesModel(it) },
            beContext.requestProduct.caloriesPerHundredGrams
        )
        assertEquals(
            BEEF_FILLED_CREATABLE_PRODUCT.proteinsPerHundredGrams?.let { ProteinsModel(it) },
            beContext.requestProduct.proteinsPerHundredGrams
        )
        assertEquals(
            BEEF_FILLED_CREATABLE_PRODUCT.fatsPerHundredGrams?.let { FatsModel(it) },
            beContext.requestProduct.fatsPerHundredGrams
        )
        assertEquals(
            BEEF_FILLED_CREATABLE_PRODUCT.carbohydratesPerHundredGrams?.let { CarbohydratesModel(it) },
            beContext.requestProduct.carbohydratesPerHundredGrams
        )
    }

    @Test
    fun createBeefResponseTestSuccess() {
        val beContext = BeContext().apply {
            requestId = REQUEST_ID_0001
            responseProduct = BEEF_FILLED_MODEL.apply {
                productId = ProductIdModel(PRODUCT_ID_0001)
                permissions.add(ProductPermissions.CREATE)
            }
        }
        val response = beContext.toCreateProductResponse()
        println(response)
        assertEquals(response::class.java.simpleName, response.messageType)
        assertEquals(REQUEST_ID_0001, response.requestId)
        assertEquals(CreateProductResponse.Result.SUCCESS, response.result)
        assertTrue(response.errors.isNullOrEmpty())
        assertTrue(response.createdProduct?.productName?.isNotBlank() ?: false)
        assertEquals(187.0, response.createdProduct?.caloriesPerHundredGrams)
        assertEquals(18.9, response.createdProduct?.proteinsPerHundredGrams)
        assertEquals(12.4, response.createdProduct?.fatsPerHundredGrams)
        assertEquals(0.0, response.createdProduct?.carbohydratesPerHundredGrams)
        assertEquals(BEEF_FILLED_MODEL.productId.asString(), response.createdProduct?.productId)
        response.createdProduct?.permissions?.let { assertContains(it, Permissions.CREATE) }
    }

    @Test
    fun createBeefResponseTestFailing() {
        val beContext = BeContext().setQuery(
            CreateProductRequest(
                requestId = REQUEST_ID_0001,
                createProduct = BEEF_NOT_CALORIES
            )
        )
        beContext.errors.add(CommonErrorModel())
        val response = beContext.toCreateProductResponse()
        println(response)
        assertNull(response.createdProduct)
    }

    @Test
    fun readBeefRequestSuccess() {
        val beContext = BeContext().setQuery(
            ReadProductRequest(
                requestId = REQUEST_ID_0001,
                readProductId = PRODUCT_ID_0001
            )
        )
        assertEquals(PRODUCT_ID_0001, beContext.requestProductId.asString())
    }

    @Test
    fun readBeefRequestFailing() {
        val beContext = BeContext().setQuery(
            ReadProductRequest(
                requestId = REQUEST_ID_0001,
                readProductId = null,
                debug = BaseDebugRequest(
                    stubCase = BaseDebugRequest.StubCase.DATABASE_ERROR
                )
            )
        )
        assertEquals(ProductIdModel.NONE, beContext.requestProductId)
        assertEquals(StubCases.DATABASE_ERROR, beContext.stubCase)
    }

    @Test
    fun readBeefResponseSuccess() {
        val beContext = BeContext().apply {
            requestId = REQUEST_ID_0001
            responseProduct = BEEF_FILLED_MODEL.apply {
                productId = ProductIdModel(PRODUCT_ID_0001)
                permissions.add(ProductPermissions.READ)
            }
        }
        val response = beContext.toReadProductResponse()
        println(response)
        assertTrue(response.errors.isNullOrEmpty())
        assertEquals(BEEF_FILLED_MODEL.productId.asString(), response.readProduct?.productId)
        assertEquals(BEEF_FILLED_MODEL.productName, response.readProduct?.productName)
        assertEquals(
            BEEF_FILLED_MODEL.caloriesPerHundredGrams,
            response.readProduct?.caloriesPerHundredGrams?.let { CaloriesModel(it) })
        assertEquals(
            BEEF_FILLED_MODEL.proteinsPerHundredGrams,
            response.readProduct?.proteinsPerHundredGrams?.let { ProteinsModel(it) })
        assertEquals(
            BEEF_FILLED_MODEL.fatsPerHundredGrams,
            response.readProduct?.fatsPerHundredGrams?.let { FatsModel(it) })
        assertEquals(
            BEEF_FILLED_MODEL.carbohydratesPerHundredGrams,
            response.readProduct?.carbohydratesPerHundredGrams?.let { CarbohydratesModel(it) })
        response.readProduct?.permissions?.let { assertContains(it, Permissions.READ) }
    }

    @Test
    fun readBeefResponseFailing() {
        val beContext = BeContext().apply {
            requestId = REQUEST_ID_0001
            errors.add(CommonErrorModel(field = "test field", message = "error message"))
        }
        val response = beContext.toReadProductResponse()
        println(response)
        assertEquals("ReadProductResponse", response.messageType)
        assertEquals(ReadProductResponse.Result.ERROR, response.result)
        assertNotNull(response.errors)
        assertNull(response.readProduct)
    }

    @Test
    fun updateBeefRequestSuccess() {
        val beContext = BeContext().setQuery(
            UpdateProductRequest(
                requestId = REQUEST_ID_0001,
                updateProduct = BEEF_FILLED_UPDATABLE_PRODUCT
            )
        )
        assertEquals(BEEF_FILLED_UPDATABLE_PRODUCT.productId, beContext.requestProduct.productId.asString())
        assertEquals(BEEF_FILLED_UPDATABLE_PRODUCT.productName, beContext.requestProduct.productName)
        assertEquals(
            BEEF_FILLED_UPDATABLE_PRODUCT.caloriesPerHundredGrams,
            beContext.requestProduct.caloriesPerHundredGrams.takeIf { it != CaloriesModel.NONE }?.value
        )
        assertEquals(
            BEEF_FILLED_UPDATABLE_PRODUCT.proteinsPerHundredGrams,
            beContext.requestProduct.proteinsPerHundredGrams.takeIf { it != ProteinsModel.NONE }?.value
        )
        assertEquals(
            BEEF_FILLED_UPDATABLE_PRODUCT.fatsPerHundredGrams,
            beContext.requestProduct.fatsPerHundredGrams.takeIf { it != FatsModel.NONE }?.value
        )
        assertEquals(
            BEEF_FILLED_UPDATABLE_PRODUCT.carbohydratesPerHundredGrams,
            beContext.requestProduct.carbohydratesPerHundredGrams.takeIf { it != CarbohydratesModel.NONE }?.value
        )
    }

    @Test
    fun updateBeefResponseSuccess() {
        val beContext = BeContext().apply {
            requestId = REQUEST_ID_0001
            responseProduct = BEEF_FILLED_MODEL.apply {
                productId = ProductIdModel(PRODUCT_ID_0001)
                permissions.add(ProductPermissions.READ)
                permissions.add(ProductPermissions.UPDATE)
            }
        }

        val response = beContext.toUpdateProductResponse()

        println(response)

        assertEquals(BEEF_FILLED_MODEL.productId.asString(), response.updateProduct?.productId)
        assertEquals(BEEF_FILLED_MODEL.productName, response.updateProduct?.productName)
        assertEquals(
            BEEF_FILLED_MODEL.caloriesPerHundredGrams,
            response.updateProduct?.caloriesPerHundredGrams?.let { CaloriesModel(it) })
        assertEquals(
            BEEF_FILLED_MODEL.proteinsPerHundredGrams,
            response.updateProduct?.proteinsPerHundredGrams?.let { ProteinsModel(it) })
        assertEquals(
            BEEF_FILLED_MODEL.fatsPerHundredGrams,
            response.updateProduct?.fatsPerHundredGrams?.let { FatsModel(it) })
        assertEquals(
            BEEF_FILLED_MODEL.carbohydratesPerHundredGrams,
            response.updateProduct?.carbohydratesPerHundredGrams?.let { CarbohydratesModel(it) }
        )
        response.updateProduct?.permissions?.let { assertContains(it, Permissions.UPDATE) }
        response.updateProduct?.permissions?.let { assertContains(it, Permissions.READ) }
    }

    @Test
    fun updateBeefResponseFailings() {
        val beContext = BeContext().setQuery(
            UpdateProductRequest(
                requestId = REQUEST_ID_0001,
                updateProduct = BEEF_NOT_FILLED_UPDATABLE_PRODUCT
            )
        )

        beContext.errors.add(CommonErrorModel())

        val response = beContext.toUpdateProductResponse()

        println(response)

        assertNull(response.updateProduct)
    }

    @Test
    fun deleteBeefRequestSuccess() {
        val beContext = BeContext().setQuery(
            DeleteProductRequest(
                requestId = REQUEST_ID_0001,
                deleteProductId = PRODUCT_ID_0001
            )
        )

        assertEquals(PRODUCT_ID_0001, beContext.requestProductId.asString())
    }

    @Test
    fun searchProductRequestSuccess() {
        val beContext = BeContext().setQuery(
            SearchProductRequest(
                requestId = REQUEST_ID_0001,
                query = "Говядина",
                debug = BaseDebugRequest(
                    stubCase = BaseDebugRequest.StubCase.SUCCESS

                )
            )
        )

        assertNotNull(beContext.foundProducts)
        assertEquals(StubCases.SUCCESS, beContext.stubCase)
    }
}