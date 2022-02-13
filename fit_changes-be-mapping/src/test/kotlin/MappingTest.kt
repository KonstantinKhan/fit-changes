import ru.fit_changes.backend.common.context.BeContext
import ru.fitChanges.backend.mapping.product.setQuery
import ru.fitChanges.backend.mapping.product.toCreateProductResponse
import ru.fitChanges.backend.mapping.product.toReadProductResponse
import ru.fitChanges.openapi.models.*
import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.backend.common.product.models.ProductPermissions
import ru.fit_changes.backend.utils.product.*
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

        println(beContext)

        assertEquals(REQUEST_ID_0001, beContext.requestId)
        assertTrue(beContext.requestProduct.productName.isNotBlank())
        assertTrue(beContext.requestProduct.caloriesPerHundredGrams.toString().isNotBlank())
        assertTrue(beContext.requestProduct.proteinsPerHundredGrams.toString().isNotBlank())
        assertTrue(beContext.requestProduct.fatsPerHundredGrams.toString().isNotBlank())
        assertTrue(beContext.requestProduct.carbohydratesPerHundredGrams.toString().isNotBlank())
    }

    @Test
    fun createBeefRequestFail() {
        val beContext = BeContext().setQuery(
            CreateProductRequest(
                requestId = REQUEST_ID_0001,
                createProduct = BEEF_NOT_FILLED_CREATABLE_PRODUCT
            )
        )
        println(beContext.errors)
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
        assertTrue(response.createProduct?.productName?.isNotBlank() ?: false)
        assertEquals(187.0, response.createProduct?.caloriesPerHundredGrams)
        assertEquals(18.9, response.createProduct?.proteinsPerHundredGrams)
        assertEquals(12.4, response.createProduct?.fatsPerHundredGrams)
        assertEquals(0.0, response.createProduct?.carbohydratesPerHundredGrams)
        assertEquals(BEEF_FILLED_MODEL.productId.asString(), response.createProduct?.productId)
        response.createProduct?.permissions?.let { assertContains(it, Permissions.CREATE) }
    }

    @Test
    fun createBeefResponseTestFail() {
        val beContext = BeContext().setQuery(
            CreateProductRequest(
                requestId = REQUEST_ID_0001,
                createProduct = BEEF_NOT_CALORIES
            )
        )
        val response = beContext.toCreateProductResponse()
        println(response)
        assertTrue(response.errors?.isNotEmpty() ?: false)
        assertEquals(CreateProductResponse.Result.ERROR, response.result)
        assertNull(response.createProduct)
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
    fun readBeefRequestFail() {
        val beContext = BeContext().setQuery(
            ReadProductRequest(
                requestId = REQUEST_ID_0001,
                readProductId = null
            )
        )
        assertEquals(ProductIdModel.NONE, beContext.requestProductId)
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
        assertEquals(BEEF_FILLED_MODEL.caloriesPerHundredGrams, response.readProduct?.caloriesPerHundredGrams)
        assertEquals(BEEF_FILLED_MODEL.proteinsPerHundredGrams, response.readProduct?.proteinsPerHundredGrams)
        assertEquals(BEEF_FILLED_MODEL.fatsPerHundredGrams, response.readProduct?.fatsPerHundredGrams)
        assertEquals(BEEF_FILLED_MODEL.carbohydratesPerHundredGrams, response.readProduct?.carbohydratesPerHundredGrams)
        response.readProduct?.permissions?.let { assertContains(it, Permissions.READ) }
    }

    @Test
    fun readBeefResponseFailure() {
        val beContext = BeContext()
        beContext.setQuery(
            ReadProductRequest(
                requestId = REQUEST_ID_0001,
                readProductId = null
            )
        )
        val response = beContext.toReadProductResponse()
        println(response)
        assertEquals("ReadProductResponse", response.messageType)
        assertEquals(ReadProductResponse.Result.SUCCESS, response.result)
        assertNull(response.errors)
        assertNull(response.readProduct)
    }
}