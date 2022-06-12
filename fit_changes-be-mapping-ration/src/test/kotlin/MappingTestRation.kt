import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.models.AuthorIdModel
import ru.fit_changes.backend.common.models.CaloriesModel
import ru.fit_changes.backend.common.models.CarbohydratesModel
import ru.fit_changes.backend.common.models.FatsModel
import ru.fit_changes.backend.common.models.ProteinsModel
import ru.fit_changes.backend.mapping.ration.setQuery
import ru.fit_changes.backend.mapping.ration.toModel
import ru.fit_changes.backend.utils.product.CREATABLE_RATION_FILLED
import ru.fit_changes.backend.utils.product.CREATABLE_RATION_WITHOUT_CARBOHYDRATES
import ru.fit_changes.backend.utils.product.REQUEST_ID_0001
import ru.fit_changes.openapi.models.CreateRationRequest
import java.time.Instant
import kotlin.test.Test
import kotlin.test.assertEquals

class MappingTestRation {

    @Test
    fun createRationSuccess() {
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
        println(beContextRation.requestRation)
        assertEquals(
            CarbohydratesModel.NONE,
            beContextRation.requestRation.meals.first().usedProducts.first().carbohydratesFact
        )
    }
}