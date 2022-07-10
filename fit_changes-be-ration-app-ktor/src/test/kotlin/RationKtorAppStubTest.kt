import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.serialization.jackson.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.fit_changes.backend.utils.product.CREATABLE_RATION_FILLED
import ru.fit_changes.openapi.models.BaseDebugRequest
import ru.fit_changes.openapi.models.CreateRationRequest
import ru.fit_changes.openapi.models.CreateRationResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class RationKtorAppStubTest {

    private fun ApplicationTestBuilder.testClient() = createClient {
        install(ContentNegotiation) {
            jackson {
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                enable(SerializationFeature.INDENT_OUTPUT)
                writerWithDefaultPrettyPrinter()
            }
        }
    }

    @Test
    fun create() = testApplication {
        val client = testClient()
        val response = client.post("/ration/create") {
            val requestObj = CreateRationRequest(
                requestId = "12345",
                createRation = CREATABLE_RATION_FILLED,
                debug = BaseDebugRequest(
                    mode = BaseDebugRequest.Mode.STUB,
                    stubCase = BaseDebugRequest.StubCase.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<CreateRationResponse>()
        assertEquals(CREATABLE_RATION_FILLED.caloriesNorm, responseObj.createdRation?.caloriesNorm)
    }
}