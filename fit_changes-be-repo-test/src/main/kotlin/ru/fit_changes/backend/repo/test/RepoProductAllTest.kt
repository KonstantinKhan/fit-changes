package ru.fit_changes.backend.repo.test

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.fit_changes.backend.common.product.models.ProductModel
import ru.fit_changes.backend.repo.product.IRepoProduct
import kotlin.test.assertEquals
import kotlin.test.assertTrue

abstract class RepoProductAllTest {
    abstract val repo: IRepoProduct

    @Test
    fun getAllSuccess() {
        val result = runBlocking { repo.allProducts() }
        assertTrue(result.isSuccess)
        assertEquals(2, result.result?.size)
        assertEquals(emptyList(), result.errors)
    }

    companion object : BaseInitProduct() {
        override val initObjects: List<ProductModel> = listOf(
            createInitTestModel("all"),
            createInitTestModel("all")
        )
    }
}