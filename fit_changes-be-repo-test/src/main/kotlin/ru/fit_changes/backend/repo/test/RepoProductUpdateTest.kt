package ru.fit_changes.backend.repo.test

import kotlinx.coroutines.runBlocking
import ru.fit_changes.backend.common.models.CommonErrorModel
import ru.fit_changes.backend.common.product.models.*
import ru.fit_changes.backend.repo.product.DbProductModelRequest
import ru.fit_changes.backend.repo.product.IRepoProduct
import java.util.*
import kotlin.test.*

abstract class RepoProductUpdateTest {
    abstract val repo: IRepoProduct

    @Test
    fun updateSuccess() {
        val result = runBlocking { repo.update(DbProductModelRequest(updateProduct)) }
        assertTrue(result.isSuccess)
        assertEquals(updateProduct, result.result)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun updateNotFound() {
        val result = runBlocking { repo.update(DbProductModelRequest(updateObjectNotFound)) }
        assertFalse(result.isSuccess)
        assertNull(result.result)
        assertEquals(listOf(CommonErrorModel(field = "id", message = "Not found")), result.errors)
    }

    companion object : BaseInitProduct() {
        override val initObjects: List<ProductModel> = listOf(createInitTestModel("update"))

        private val updateIdNotFound = ProductIdModel(UUID.randomUUID())

        private val updateProduct = ProductModel(
            productId = initObjects.first().productId,
            authorId = initObjects.first().authorId,
            productName = "Updated Chicken",
            caloriesPerHundredGrams = CaloriesModel(111.0),
            proteinsPerHundredGrams = ProteinsModel(21.0),
            fatsPerHundredGrams = FatsModel(2.5),
            carbohydratesPerHundredGrams = CarbohydratesModel(0.0)
        )

        private val updateObjectNotFound = ProductModel(
            productId = updateIdNotFound,
            productName = "update object not found",
            caloriesPerHundredGrams = CaloriesModel(234.0),
            proteinsPerHundredGrams = ProteinsModel(13.0),
            fatsPerHundredGrams = FatsModel(14.8),
            carbohydratesPerHundredGrams = CarbohydratesModel(12.0)
        )

    }
}