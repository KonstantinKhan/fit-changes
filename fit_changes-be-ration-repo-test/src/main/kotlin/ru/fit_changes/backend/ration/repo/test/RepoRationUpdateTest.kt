package ru.fit_changes.backend.ration.repo.test

import kotlinx.coroutines.runBlocking
import ru.fit_changes.backend.common.models.CaloriesModel
import ru.fit_changes.backend.common.models.ration.RationModel
import ru.fit_changes.backend.repo.ration.DbRationModelRequest
import ru.fit_changes.backend.repo.ration.IRepoRation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

abstract class RepoRationUpdateTest {
    abstract val repo: IRepoRation

    @Test
    fun updateRationSuccess() {
        val result = runBlocking { repo.update(DbRationModelRequest(updateRation)) }
        assertTrue(result.isSuccess)
        assertTrue(result.errors.isEmpty())
        assertEquals(updateRation, result.result)
        println(result.result?.caloriesNorm)
    }

    companion object : BaseInitRation() {
        override val initObjects: List<RationModel> = listOf(createInitTestModel())
        private val updateRation = initObjects.first().copy(
            caloriesNorm = CaloriesModel(2200.0)
        )
    }
}