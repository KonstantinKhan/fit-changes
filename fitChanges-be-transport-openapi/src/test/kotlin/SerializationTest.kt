import com.fasterxml.jackson.databind.ObjectMapper
import ru.fitChanges.backend.utils.product.BEEF_FILLED
import ru.fitChanges.openapi.models.BaseMessage
import ru.fitChanges.openapi.models.CreateProductRequest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class SerializationTest {

    private val requestId = "rID:0001"

    // Test example of a CreateProductRequest
    private val createBeef = CreateProductRequest(
        requestId = requestId,
        createProduct = BEEF_FILLED
    )

    private val om = ObjectMapper()
    private val json = om.writeValueAsString(createBeef)

    @Test
    fun serializationProductTest() {
        println(json)
        assertContains(json, Regex("messageType\":\\s*\"${CreateProductRequest::class.simpleName}"))
    }

    @Test
    fun deserializationProductTest() {
        val deserialized = om.readValue(json, BaseMessage::class.java) as CreateProductRequest
        println(deserialized)
        assertEquals(requestId, deserialized.requestId)
    }
}