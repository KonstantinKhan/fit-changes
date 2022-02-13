import ru.fit_changes.backend.common.context.BeContext
import ru.fitChanges.backend.mapping.product.setQuery
import ru.fitChanges.backend.mapping.product.toCreateProductResponse
import ru.fitChanges.openapi.models.CreateProductRequest
import ru.fitChanges.openapi.models.CreateProductResponse
import ru.fit_changes.backend.utils.product.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MappingTest {

    private val beContext = BeContext()

    @Test
    fun createBeefRequestSuccess() {
        beContext.setQuery(
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

    @Test()
    fun createBeefRequestFail() {
        beContext.setQuery(
            CreateProductRequest(
                requestId = REQUEST_ID_0001,
                createProduct = BEEF_NOT_FILLED_CREATABLE_PRODUCT
            )
        )
        println(beContext)
    }

    @Test
    fun createBeefResponseTestSuccess() {
        beContext.apply {
            requestId = REQUEST_ID_0001
            responseProduct = BEEF_FILLED_MODEL
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
        assertEquals(PRODUCT_ID_0001, response.createProduct?.productId)
        assertTrue(response.createProduct?.permissions?.isNotEmpty() ?: false)
    }

    @Test
    fun createBeefResponseTestFail() {
        beContext.setQuery(
            CreateProductRequest(
                requestId = REQUEST_ID_0001,
                createProduct = BEEF_NOT_FILLED_CREATABLE_PRODUCT
            )
        )
        val response = beContext.toCreateProductResponse()
        println(beContext.requestProduct)
        println(response)
        assertTrue(response.errors?.isNotEmpty() ?: false)
        assertEquals(null, response.createProduct)
    }

}