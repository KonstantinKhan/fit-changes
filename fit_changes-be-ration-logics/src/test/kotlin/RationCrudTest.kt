import kotlinx.coroutines.runBlocking
import ru.fit_changes.backend.common.context.BeContextRation
import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.backend.common.models.enums.StubCases
import ru.fit_changes.backend.common.models.ration.RationIdModel
import ru.fit_changes.backend.common.models.ration.RationSearchFilter
import ru.fit_changes.backend.ration.logics.RationCrud
import ru.fit_changes.backend.utils.product.RATION_FILLED_MODEL
import ru.fit_changes.backend.utils.product.RATION_ID
import kotlin.test.Test
import kotlin.test.assertEquals

class RationCrudTest {

    @Test
    fun rationCreateSuccess() {
        val crud = RationCrud()
        val context = BeContextRation(
            operation = Operations.CREATE,
            stubCase = StubCases.SUCCESS,
            requestRation = RATION_FILLED_MODEL
        )

        runBlocking {
            crud.create(context)
            val expected = RATION_FILLED_MODEL.apply {
                rationId = RationIdModel(RATION_ID)
            }

            with(context.responseRation) {
                assertEquals(expected.rationId, rationId)
                assertEquals(expected.authorId, authorId)
                assertEquals(expected.dateRation, dateRation)
                assertEquals(expected.caloriesNorm, caloriesNorm)
                assertEquals(expected.proteinsNorm, proteinsNorm)
                assertEquals(expected.fatsNorm, fatsNorm)
                assertEquals(expected.carbohydratesNorm, carbohydratesNorm)
                assertEquals(expected.caloriesFact, caloriesFact)
                assertEquals(expected.proteinsFact, proteinsFact)
                assertEquals(expected.fatsFact, fatsFact)
                assertEquals(expected.carbohydratesFact, carbohydratesFact)
                assertEquals(expected.meals, meals)
            }
        }
    }

    @Test
    fun rationReadSuccess() {
        val crud = RationCrud()
        val context = BeContextRation(
            operation = Operations.READ,
            stubCase = StubCases.SUCCESS,
            requestRationId = RationIdModel(RATION_ID)
        )
        runBlocking {
            crud.read(context)
            val expected = RATION_FILLED_MODEL.copy(
                rationId = RationIdModel(RATION_ID)
            )

            with(context.responseRation) {
                assertEquals(expected.rationId, rationId)
                assertEquals(expected.authorId, authorId)
                assertEquals(expected.dateRation, dateRation)
                assertEquals(expected.caloriesNorm, caloriesNorm)
                assertEquals(expected.proteinsNorm, proteinsNorm)
                assertEquals(expected.fatsNorm, fatsNorm)
                assertEquals(expected.carbohydratesNorm, carbohydratesNorm)
                assertEquals(expected.caloriesFact, caloriesFact)
                assertEquals(expected.proteinsFact, proteinsFact)
                assertEquals(expected.fatsFact, fatsFact)
                assertEquals(expected.carbohydratesFact, carbohydratesFact)
                assertEquals(expected.meals, meals)
            }
        }
    }

    @Test
    fun rationUpdateSuccess() {
        val crud = RationCrud()
        val context = BeContextRation(
            operation = Operations.UPDATE,
            stubCase = StubCases.SUCCESS,
            requestRation = RATION_FILLED_MODEL.copy(rationId = RationIdModel(RATION_ID))
        )
        val expected = RATION_FILLED_MODEL.copy(rationId = RationIdModel(RATION_ID))

        runBlocking {
            crud.update(context)
            with(context.responseRation) {
                assertEquals(expected.rationId, rationId)
                assertEquals(expected.authorId, authorId)
                assertEquals(expected.dateRation, dateRation)
                assertEquals(expected.caloriesNorm, caloriesNorm)
                assertEquals(expected.proteinsNorm, proteinsNorm)
                assertEquals(expected.fatsNorm, fatsNorm)
                assertEquals(expected.carbohydratesNorm, carbohydratesNorm)
                assertEquals(expected.caloriesFact, caloriesFact)
                assertEquals(expected.proteinsFact, proteinsFact)
                assertEquals(expected.fatsFact, fatsFact)
                assertEquals(expected.carbohydratesFact, carbohydratesFact)
                assertEquals(expected.meals, meals)
            }
        }
    }

    @Test
    fun rationDeleteSuccess() {
        val crud = RationCrud()
        val context = BeContextRation(
            operation = Operations.DELETE,
            stubCase = StubCases.SUCCESS,
            requestRationId = RationIdModel(RATION_ID)
        )
        val expected = RATION_FILLED_MODEL.copy(rationId = RationIdModel(RATION_ID))

        runBlocking {
            crud.delete(context)
            with(context.responseRation) {
                assertEquals(expected.rationId, rationId)
                assertEquals(expected.authorId, authorId)
                assertEquals(expected.dateRation, dateRation)
                assertEquals(expected.caloriesNorm, caloriesNorm)
                assertEquals(expected.proteinsNorm, proteinsNorm)
                assertEquals(expected.fatsNorm, fatsNorm)
                assertEquals(expected.carbohydratesNorm, carbohydratesNorm)
                assertEquals(expected.caloriesFact, caloriesFact)
                assertEquals(expected.proteinsFact, proteinsFact)
                assertEquals(expected.fatsFact, fatsFact)
                assertEquals(expected.carbohydratesFact, carbohydratesFact)
                assertEquals(expected.meals, meals)
            }
        }
    }

    @Test
    fun searchRationSuccess() {
        val crud = RationCrud()
        val context = BeContextRation(
            operation = Operations.SEARCH,
            stubCase = StubCases.SUCCESS,
            requestRationFilter = RationSearchFilter("курин")
        )
        val expected = RATION_FILLED_MODEL.copy(rationId = RationIdModel(RATION_ID))

        runBlocking {
            crud.search(context)
            assertEquals(1, context.foundRations.size)
            with(context.foundRations.first()) {
                assertEquals(expected.rationId, rationId)
                assertEquals(expected.authorId, authorId)
                assertEquals(expected.dateRation, dateRation)
                assertEquals(expected.caloriesNorm, caloriesNorm)
                assertEquals(expected.proteinsNorm, proteinsNorm)
                assertEquals(expected.fatsNorm, fatsNorm)
                assertEquals(expected.carbohydratesNorm, carbohydratesNorm)
                assertEquals(expected.caloriesFact, caloriesFact)
                assertEquals(expected.proteinsFact, proteinsFact)
                assertEquals(expected.fatsFact, fatsFact)
                assertEquals(expected.carbohydratesFact, carbohydratesFact)
                assertEquals(expected.meals, meals)
            }
        }
    }
}