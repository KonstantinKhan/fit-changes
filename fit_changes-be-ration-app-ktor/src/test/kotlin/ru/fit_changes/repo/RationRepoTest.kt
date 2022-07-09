package ru.fit_changes.repo

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import ru.fit_changes.backend.common.mappers.toModel
import ru.fit_changes.backend.common.models.ration.RationIdModel
import ru.fit_changes.backend.utils.product.CREATABLE_RATION_FILLED
import ru.fit_changes.openapi.models.BaseDebugRequest
import ru.fit_changes.openapi.models.CreateRationRequest
import ru.fit_changes.openapi.models.CreateRationResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RationRepoTest {

    @Test
    fun addToDb() = testApplication {

        environment {
            config = ApplicationConfig("test.conf")
        }

        val expected = CREATABLE_RATION_FILLED.toModel()
        val client = testClient()
        val response = client.post("/ration/create") {
            val requestObject = CreateRationRequest(
                requestId = "rID:0001",
                createRation = CREATABLE_RATION_FILLED,
                debug = BaseDebugRequest(
                    mode = BaseDebugRequest.Mode.TEST
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObject)
        }
        val responseObject = response.body<CreateRationResponse>()
        expected.rationId = responseObject.createdRation?.rationId?.let { RationIdModel(it) } ?: RationIdModel.NONE
        assertTrue(responseObject.errors.isNullOrEmpty())
        assertEquals(CreateRationResponse.Result.SUCCESS, responseObject.result)
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expected.rationId.asString(), responseObject.createdRation?.rationId)
    }
}