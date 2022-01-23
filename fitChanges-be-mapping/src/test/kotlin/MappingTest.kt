import ru.fitChanges.backend.common.product.context.BeContext
import ru.fitChanges.backend.mapping.product.setQuery
import ru.fitChanges.backend.utils.product.BEEF_FILLED
import ru.fitChanges.backend.utils.product.BEEF_NOT_FILLED
import ru.fitChanges.backend.utils.product.REQUEST_ID_0001
import ru.fitChanges.openapi.models.CreateProductRequest
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
                createProduct = BEEF_FILLED
            )
        )

        println(beContext)

        assertEquals(REQUEST_ID_0001, beContext.requestId)
        assertTrue(beContext.requestProduct.productName.isNotBlank())
        assertTrue(beContext.requestProduct.caloriesPerHundredGrams.toString().isNotBlank())
        assertTrue(beContext.requestProduct.proteinsPerHundredGrams.toString().isNotBlank())
        assertTrue(beContext.requestProduct.fatsPerHundredGrams.toString().isNotBlank())
        assertTrue(beContext.requestProduct.carbohydratePerHundredGrams.toString().isNotBlank())
    }

    @Test()
    fun createBeefRequestFail() {
        beContext.setQuery(
            CreateProductRequest(
                requestId = REQUEST_ID_0001,
                createProduct = BEEF_NOT_FILLED
            )
        )
        println(beContext)
    }
}