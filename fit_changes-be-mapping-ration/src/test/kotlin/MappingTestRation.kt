import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.mappers.toModel
import ru.fit_changes.backend.common.models.AuthorIdModel
import ru.fit_changes.backend.common.models.CaloriesModel
import ru.fit_changes.backend.common.models.CarbohydratesModel
import ru.fit_changes.backend.common.models.FatsModel
import ru.fit_changes.backend.common.models.ProteinsModel
import ru.fit_changes.backend.common.models.ration.RationIdModel
import ru.fit_changes.backend.common.models.ration.RationSearchFilter
import ru.fit_changes.backend.mapping.ration.setQuery
import ru.fit_changes.backend.mapping.ration.toCreateRationResponse
import ru.fit_changes.backend.utils.product.*
import ru.fit_changes.openapi.models.*
import java.time.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MappingTestRation {

    @Test
    fun createRationRequestSuccess() {
        val beContextRation = BeContextRation().setQuery(
            CreateRationRequest(
                requestId = REQUEST_ID_0001,
                createRation = CREATABLE_RATION_FILLED
            )
        )

        assertEquals(REQUEST_ID_0001, beContextRation.requestId)
        with(beContextRation.requestRation) {
            assertEquals(CREATABLE_RATION_FILLED.authorId?.let { AuthorIdModel(it) }, authorId)
            assertEquals(CREATABLE_RATION_FILLED.dateRation?.let { Instant.parse(it) }, dateRation)
            assertEquals(CREATABLE_RATION_FILLED.caloriesNorm?.let { CaloriesModel(it) }, caloriesNorm)
            assertEquals(CREATABLE_RATION_FILLED.proteinsNorm?.let { ProteinsModel(it) }, proteinsNorm)
            assertEquals(CREATABLE_RATION_FILLED.fatsNorm?.let { FatsModel(it) }, fatsNorm)
            assertEquals(CREATABLE_RATION_FILLED.carbohydratesNorm?.let { CarbohydratesModel(it) }, carbohydratesNorm)
            assertEquals(CREATABLE_RATION_FILLED.caloriesFact?.let { CaloriesModel(it) }, caloriesFact)
            assertEquals(CREATABLE_RATION_FILLED.proteinsFact?.let { ProteinsModel(it) }, proteinsFact)
            assertEquals(CREATABLE_RATION_FILLED.fatsFact?.let { FatsModel(it) }, fatsFact)
            assertEquals(CREATABLE_RATION_FILLED.carbohydratesFact?.let { CarbohydratesModel(it) }, carbohydratesFact)
            assertEquals(CREATABLE_RATION_FILLED.meals?.map { it.toModel() }?.toList(), meals)
        }
    }

    @Test
    fun createRationFailures() {
        val beContextRation = BeContextRation().setQuery(
            CreateRationRequest(
                requestId = REQUEST_ID_0001,
                createRation = CREATABLE_RATION_WITHOUT_CARBOHYDRATES
            )
        )
        assertEquals(
            CarbohydratesModel.NONE,
            beContextRation.requestRation.meals.first().usedProducts.first().carbohydratesFact
        )
    }

    @Test
    fun readRationSuccess() {
        val beContextRation = BeContextRation().setQuery(
            ReadRationRequest(
                requestId = REQUEST_ID_0001,
                readRationId = RATION_ID
            )
        )
        assertEquals(REQUEST_ID_0001, beContextRation.requestId)
        assertEquals(RationIdModel(RATION_ID), beContextRation.requestRationId)
    }

    @Test
    fun updateRationSuccess() {
        val beContextRation = BeContextRation().setQuery(
            UpdateRationRequest(
                requestId = REQUEST_ID_0001,
                updateRation = UPDATABLE_RATION_FILLED
            )
        )
        assertEquals(REQUEST_ID_0001, beContextRation.requestId)
        with(beContextRation.requestRation) {
            assertEquals(UPDATABLE_RATION_FILLED.rationId?.let { RationIdModel(it) }, rationId)
            assertEquals(UPDATABLE_RATION_FILLED.authorId?.let { AuthorIdModel(it) }, authorId)
            assertEquals(UPDATABLE_RATION_FILLED.dateRation?.let { Instant.parse(it) }, dateRation)
            assertEquals(UPDATABLE_RATION_FILLED.caloriesNorm?.let { CaloriesModel(it) }, caloriesNorm)
            assertEquals(UPDATABLE_RATION_FILLED.proteinsNorm?.let { ProteinsModel(it) }, proteinsNorm)
            assertEquals(UPDATABLE_RATION_FILLED.fatsNorm?.let { FatsModel(it) }, fatsNorm)
            assertEquals(UPDATABLE_RATION_FILLED.carbohydratesNorm?.let { CarbohydratesModel(it) }, carbohydratesNorm)
            assertEquals(UPDATABLE_RATION_FILLED.caloriesFact?.let { CaloriesModel(it) }, caloriesFact)
            assertEquals(UPDATABLE_RATION_FILLED.proteinsFact?.let { ProteinsModel(it) }, proteinsFact)
            assertEquals(UPDATABLE_RATION_FILLED.fatsFact?.let { FatsModel(it) }, fatsFact)
            assertEquals(UPDATABLE_RATION_FILLED.carbohydratesFact?.let { CarbohydratesModel(it) }, carbohydratesFact)
            assertEquals(UPDATABLE_RATION_FILLED.meals?.map { it.toModel() }?.toList(), meals)
        }
    }

    @Test
    fun deleteRationSuccess() {
        val beContextRation = BeContextRation().setQuery(
            DeleteRationRequest(
                requestId = REQUEST_ID_0001,
                deleteRationId = RATION_ID
            )
        )

        assertEquals(REQUEST_ID_0001, beContextRation.requestId)
        assertEquals(RationIdModel(RATION_ID), beContextRation.requestRationId)
    }

    @Test
    fun searchRationSuccess() {
        val beContextRation = BeContextRation().setQuery(
            SearchRationRequest(
                requestId = REQUEST_ID_0001,
                query = "филе",
            )
        )

        assertEquals(REQUEST_ID_0001, beContextRation.requestId)
        assertEquals(RationSearchFilter(searchString = "филе"), beContextRation.requestRationFilter)
    }

    @Test
    fun createRationResponseSuccess() {
        val beContextRation = BeContextRation().apply {
            requestId = REQUEST_ID_0001
            responseRation = RATION_FILLED_MODEL.apply {
                rationId = RationIdModel(RATION_ID)
            }
        }

        val response = beContextRation.toCreateRationResponse()
        println(response)
        with(response) {
            assertEquals(this::class.simpleName, response.messageType)
            assertEquals(REQUEST_ID_0001, requestId)
            assertEquals(CreateRationResponse.Result.SUCCESS, result)
            assertTrue(errors.isNullOrEmpty())
            assertFalse(createdRation?.meals.isNullOrEmpty())
        }
    }
}