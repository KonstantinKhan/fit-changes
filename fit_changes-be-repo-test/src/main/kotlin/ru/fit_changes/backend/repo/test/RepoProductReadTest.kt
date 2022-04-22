package ru.fit_changes.backend.repo.test

import kotlinx.coroutines.runBlocking
import ru.fit_changes.backend.common.models.CommonErrorModel
import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.backend.common.product.models.ProductModel
import ru.fit_changes.backend.repo.product.DbProductIdRequest
import ru.fit_changes.backend.repo.product.IRepoProduct
import java.util.*
import kotlin.test.*

abstract class RepoProductReadTest {
    abstract val repo: IRepoProduct

    @Test
    fun readSuccess() {
        val result = runBlocking { repo.read(DbProductIdRequest(idSuccess)) }
        assertTrue(result.isSuccess)
        assertTrue(result.errors.isEmpty())
        assertEquals(readSuccessStub, result.result)
    }

    @Test
    fun readFailure() {
        val result = runBlocking { repo.read(DbProductIdRequest(idNotFound)) }
        assertFalse(result.isSuccess)
        assertNull(result.result)
        assertEquals(listOf(CommonErrorModel(field = "id", message = "Id not found")), result.errors)
    }

    companion object : BaseInitProduct() {
        override val initObjects: List<ProductModel> = listOf(createInitTestModel("read", "Chicken"))
        private val readSuccessStub = initObjects.first()
        private val idSuccess = readSuccessStub.productId
        private val idNotFound = ProductIdModel(UUID.randomUUID())
    }
}
