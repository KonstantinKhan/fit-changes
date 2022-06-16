import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ru.fit_changes.backend.utils.product.CREATABLE_RATION_FILLED
import ru.fit_changes.backend.utils.product.REQUEST_ID_0001
import ru.fit_changes.openapi.models.BaseMessage
import ru.fit_changes.openapi.models.CreateRationRequest
import ru.fit_changes.openapi.models.ReadRationRequest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class SerializationRationTest {

    private val createRation = CreateRationRequest(
        requestId = REQUEST_ID_0001,
        createRation = CREATABLE_RATION_FILLED
    )

    private val readRation = ReadRationRequest(
        requestId = REQUEST_ID_0001,
        readRationId = "rationID:0001"
    )

    private val om = jacksonObjectMapper()
    private val jsonCreateRation = om.writeValueAsString(createRation)
    private val jsonReadRation = om.writeValueAsString(readRation)

    @Test
    fun serializationCreateRationTest() {
        println(jsonCreateRation)
        assertContains(jsonCreateRation, Regex("messageType\":\\s*\"${CreateRationRequest::class.simpleName}"))
    }

    @Test
    fun deserializationReadProductTest() {
        val deserialized = om.readValue(jsonReadRation, BaseMessage::class.java) as ReadRationRequest
        println(deserialized)
        assertEquals(REQUEST_ID_0001, deserialized.requestId)
        assertEquals("rationID:0001", deserialized.readRationId)
    }
}