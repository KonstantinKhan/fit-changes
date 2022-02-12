import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ru.fit_changes.backend.utils.product.BEEF_FILLED_CREATABLE_PRODUCT
import ru.fit_changes.backend.utils.product.PRODUCT_ID_0001
import ru.fit_changes.backend.utils.product.REQUEST_ID_0001
import ru.fitChanges.openapi.models.BaseMessage
import ru.fitChanges.openapi.models.CreateProductRequest
import ru.fitChanges.openapi.models.ReadProductRequest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class SerializationTest {

    // Test example of a CreateProductRequest
    private val createBeef = CreateProductRequest(
        requestId = REQUEST_ID_0001,
        createProduct = BEEF_FILLED_CREATABLE_PRODUCT
    )

    // Test example of a ReadProductRequest
    private val readBeef = ReadProductRequest(
        requestId = REQUEST_ID_0001,
        readProductId = PRODUCT_ID_0001
    )

    private val om = jacksonObjectMapper()
    private val jsonCreateBeef = om.writeValueAsString(createBeef)
    private val jsonReadBeef = om.writeValueAsString(readBeef)

    @Test
    fun serializationCreateProductTest() {
        println(jsonCreateBeef)
        assertContains(jsonCreateBeef, Regex("messageType\":\\s*\"${CreateProductRequest::class.simpleName}"))
    }

    @Test
    fun deserializationCreateProductTest() {
        val deserialized = om.readValue(jsonCreateBeef, BaseMessage::class.java) as CreateProductRequest
        println(deserialized)
        assertEquals(REQUEST_ID_0001, deserialized.requestId)
    }

    @Test
    fun serializationReadProductTest() {
        println(jsonReadBeef)
        assertContains(jsonReadBeef, Regex("messageType\":\\s*\"${ReadProductRequest::class.java.simpleName}"))
    }

    @Test
    fun deserializationReadProductTest() {
        val deserialized = om.readValue(jsonReadBeef, BaseMessage::class.java) as ReadProductRequest
        println(deserialized)
        assertEquals(REQUEST_ID_0001, deserialized.requestId)
        assertEquals(PRODUCT_ID_0001, deserialized.readProductId)
    }
}