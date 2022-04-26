package ru.fit_changes.backend.repo.test

import kotlinx.coroutines.runBlocking
import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.backend.repo.product.DbProductFilterRequest
import ru.fit_changes.backend.repo.product.IRepoProduct
import ru.fit_changes.backend.utils.product.BEEF_FILLED_MODEL
import ru.fit_changes.backend.utils.product.CHICKEN_FILLED_MODEL
import ru.fit_changes.backend.utils.product.CHICKEN_THIGH_FILLED_MODEL
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

abstract class RepoProductSearchTest {
    abstract val repo: IRepoProduct

    @Test
    fun searchSuccessWithoutQuery() {
        val result = runBlocking { repo.search(DbProductFilterRequest()) }
        assertTrue(result.isSuccess)
    }

    @Test
    fun searchSuccessBeef() {
        val result = runBlocking { repo.search(DbProductFilterRequest("Гов")) }
        assertTrue(result.isSuccess)
        assertTrue(result.result?.size == 1)
        assertEquals(initObjects.first(), result.result?.first())
    }

    @Test
    fun searchSuccessChicken() {
        val result = runBlocking { repo.search(DbProductFilterRequest("кур")) }
        assertEquals(2, result.result?.size)
    }

    companion object : BaseInitProduct() {
        override val initObjects = listOf(
            BEEF_FILLED_MODEL.copy(
                productId = ProductIdModel(UUID.randomUUID())
            ),
            CHICKEN_FILLED_MODEL.copy(
                productId = ProductIdModel(UUID.randomUUID())
            ),
            CHICKEN_THIGH_FILLED_MODEL.copy(
                productId = ProductIdModel(UUID.randomUUID())
            )
        )
    }
}