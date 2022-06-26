package ru.fit_changes.backend.ration.repo.test

import kotlinx.coroutines.runBlocking
import ru.fit_changes.backend.common.models.ration.RationModel
import ru.fit_changes.backend.repo.ration.DbRationIdRequest
import ru.fit_changes.backend.repo.ration.IRepoRation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

abstract class RepoRationReadTest {
    abstract val repo: IRepoRation

    @Test
    fun readRationSuccess() {
        val result = runBlocking { repo.read(DbRationIdRequest(idSuccess)) }
        assertTrue(result.isSuccess)
        assertTrue(result.errors.isEmpty())
        assertEquals(readSuccessStub, result.result)
    }

    companion object : BaseInitRation() {
        override val initObjects: List<RationModel> = listOf(createInitTestModel())
        private val idSuccess = initObjects.first().rationId
        private val readSuccessStub = initObjects.first()
    }

}