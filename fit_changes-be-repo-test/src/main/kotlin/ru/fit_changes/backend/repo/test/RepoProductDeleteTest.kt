package ru.fit_changes.backend.repo.test

import kotlinx.coroutines.runBlocking
import ru.fit_changes.backend.common.models.CommonErrorModel
import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.backend.common.product.models.ProductModel
import ru.fit_changes.backend.repo.product.DbProductIdRequest
import ru.fit_changes.backend.repo.product.IRepoProduct
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

abstract class RepoProductDeleteTest {
    abstract val repo: IRepoProduct

    @Test
    fun deleteSuccess() {
        val result = runBlocking { repo.delete(DbProductIdRequest(idSuccess)) }
        assertTrue(result.isSuccess)
        assertEquals(initObjects.first(), result.result)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun deleteFailure() {
        val result = runBlocking { repo.delete(DbProductIdRequest(idNotFound)) }
        assertFalse(result.isSuccess)
        assertEquals(listOf(CommonErrorModel(field = "id", message = "Id not found")), result.errors)
    }

    companion object : BaseInitProduct() {
        override val initObjects: List<ProductModel> = listOf(createInitTestModel("delete"))

        private val idSuccess = initObjects.first().productId
        private val idNotFound = ProductIdModel(UUID.randomUUID())
    }
}