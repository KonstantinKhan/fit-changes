package ru.fit_changes.backend.ration.repo.test

import kotlinx.coroutines.runBlocking
import ru.fit_changes.backend.common.models.AuthorIdModel
import ru.fit_changes.backend.common.models.ration.RationIdModel
import ru.fit_changes.backend.repo.ration.DbRationModelRequest
import ru.fit_changes.backend.repo.ration.IRepoRation
import ru.fit_changes.backend.utils.product.RATION_FILLED_MODEL
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

abstract class RepoRationCreateTest {
    abstract val repo: IRepoRation

    @Test
    fun createSuccess() {
        val result = runBlocking { repo.create(DbRationModelRequest(createInitTestModel("create"))) }
        val expected = createObject.copy(
            rationId = result.result?.rationId ?: RationIdModel.NONE,
            authorId = result.result?.authorId ?: AuthorIdModel.NONE
        )
        assertTrue(result.isSuccess)
        assertEquals(expected, result.result)
    }

    companion object : BaseInitRation() {

        val createObject = RATION_FILLED_MODEL

        override val initObjects = listOf(
            RATION_FILLED_MODEL
        )
    }
}