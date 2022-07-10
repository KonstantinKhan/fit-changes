package ru.fit_changes.repo

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import ru.fit_changes.backend.app.ktor.ration.test
import ru.fit_changes.backend.common.mappers.toModel
import ru.fit_changes.backend.common.models.ration.RationIdModel
import ru.fit_changes.backend.utils.product.CREATABLE_RATION_FILLED
import ru.fit_changes.backend.utils.product.UPDATABLE_RATION_FILLED
import ru.fit_changes.openapi.models.*
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RationRepoTest {

    @Test
    fun addToDb() = testApplication {

        environment {
            config = ApplicationConfig("test.conf")
        }

        application {
            test(UUID.randomUUID().toString())
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

    @Test
    fun addToDbFailure() = testApplication {
        environment {
            config = ApplicationConfig("test.conf")
        }

        val client = testClient()
        val response = client.post("/ration/create") {
            val requestObject = CreateRationRequest(
                requestId = "rID:0001",
                createRation = CREATABLE_RATION_FILLED.copy(
                    caloriesFact = null
                ),
                debug = BaseDebugRequest(
                    mode = BaseDebugRequest.Mode.TEST
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObject)
        }
        val responseObject = response.body<CreateRationResponse>()
        println(responseObject)
        assertFalse(responseObject.errors.isNullOrEmpty())
    }

    @Test
    fun readFromDb() = testApplication {

        val requestRationId = UUID.randomUUID().toString()

        environment {
            config = ApplicationConfig("test.conf")
        }

        application {
            test(requestRationId)
        }

        val client = testClient()
        val response = client.post("/ration/read") {
            val requestObject = ReadRationRequest(
                requestId = "rID:0001",
                readRationId = requestRationId,
                debug = BaseDebugRequest(
                    mode = BaseDebugRequest.Mode.TEST
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObject)
        }
        val responseObject = response.body<ReadRationResponse>()

        assertTrue(responseObject.errors.isNullOrEmpty())
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(requestRationId, responseObject.readRation?.rationId)
    }

    @Test
    fun repoUpdateRation() = testApplication {

        val requestRationId = UUID.randomUUID().toString()
        val expected = UPDATABLE_RATION_FILLED.copy(rationId = requestRationId).toModel()

        environment {
            config = ApplicationConfig("test.conf")
        }

        application {
            test(requestRationId)
        }

        val client = testClient()

        val response = client.post("/ration/update") {
            val requestObject = UpdateRationRequest(
                requestId = "rID:0001",
                updateRation = UPDATABLE_RATION_FILLED.copy(rationId = requestRationId),
                debug = BaseDebugRequest(
                    mode = BaseDebugRequest.Mode.TEST
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObject)
        }

        val responseObject = response.body<UpdateRationResponse>()

        assertTrue(responseObject.errors.isNullOrEmpty())
        assertEquals(UpdateRationResponse.Result.SUCCESS, responseObject.result)
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expected.rationId.asString(), responseObject.updatedRation?.rationId)
        assertEquals(expected.caloriesFact.value, responseObject.updatedRation?.caloriesFact)
    }

    @Test
    fun repoDeleteRation() = testApplication {
        val requestRationId = UUID.randomUUID().toString()
        environment {
            config = ApplicationConfig("test.conf")
        }
        application {
            test(requestRationId)
        }
        val client = testClient()
        val response = client.post("/ration/delete") {
            val requestObject = DeleteRationRequest(
                requestId = "rID:0001",
                deleteRationId = requestRationId,
                debug = BaseDebugRequest(
                    mode = BaseDebugRequest.Mode.TEST
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObject)
        }
        val responseObject = response.body<DeleteRationResponse>()
        assertTrue(responseObject.errors.isNullOrEmpty())
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(requestRationId, responseObject.deletedRation?.rationId)
    }

    @Test
    fun repoSearchRation() = testApplication {
        environment {
            config = ApplicationConfig("test.conf")
        }
        application {
            test(UUID.randomUUID().toString())
        }
        val client = testClient()
        val response = client.post("/ration/search") {
            val requestObject = SearchRationRequest(
                requestId = "rID:0001",
                query = "2022-06-12T08:00:00.0Z",
                debug = BaseDebugRequest(
                    mode = BaseDebugRequest.Mode.TEST
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObject)
        }
        val responseObject = response.body<SearchRationResponse>()
        println(responseObject.foundRations)
    }
}