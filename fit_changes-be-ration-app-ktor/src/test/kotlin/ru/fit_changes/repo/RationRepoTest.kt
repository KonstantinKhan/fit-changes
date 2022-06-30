package ru.fit_changes.repo

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import ru.fit_changes.backend.app.ktor.ration.AppKtorConfig
import ru.fit_changes.backend.app.ktor.ration.module
import ru.fit_changes.backend.common.context.RationContextConfig
import ru.fit_changes.backend.common.models.AuthorIdModel
import ru.fit_changes.backend.common.models.ration.RationIdModel
import ru.fit_changes.backend.ration.repo.inmemory.RepoRationInMemory
import ru.fit_changes.backend.utils.product.CREATABLE_RATION_FILLED
import ru.fit_changes.backend.utils.product.RATION_FILLED_MODEL
import ru.fit_changes.openapi.models.BaseDebugRequest
import ru.fit_changes.openapi.models.CreateRationRequest
import ru.fit_changes.openapi.models.CreateRationResponse
import java.util.*
import kotlin.test.Test

class RationRepoTest {

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
    fun addToDb() = testApplication {
        application {
            module(
                config = AppKtorConfig(
                    contextConfig = RationContextConfig(
                        repoRationTest = RepoRationInMemory(
                            listOf(
                                RATION_FILLED_MODEL.copy(
                                    rationId = RationIdModel(UUID.randomUUID()),
                                    authorId = AuthorIdModel(UUID.randomUUID())
                                )
                            )
                        ),
                    )

                )
            )
        }
        val client = testClient()
        val response = client.post("/ration/create") {
            val requestObject = CreateRationRequest(
                requestId = "rID:0001",
                createRation = CREATABLE_RATION_FILLED.copy(
                    authorId = "authorId:0001"
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
    }
}