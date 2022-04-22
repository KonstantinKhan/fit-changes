package ru.fit_changes.backend.repo.test

import kotlinx.coroutines.runBlocking
import ru.fit_changes.backend.common.product.models.*
import ru.fit_changes.backend.repo.product.DbProductModelRequest
import ru.fit_changes.backend.repo.product.IRepoProduct
import kotlin.test.*

abstract class RepoProductCreateTest {
    abstract val repo: IRepoProduct

    @Test
    fun createSuccess() {
        val result = runBlocking { repo.create(DbProductModelRequest(createInitTestModel("create", "Chicken"))) }
        val expected = createObject.copy(productId = result.result?.productId ?: ProductIdModel.NONE)
        assertEquals(expected, result.result)
    }

    @Test
    fun createFailing() {
        val result = runBlocking {
            repo.create(DbProductModelRequest(createObject.apply {
                productName = ""
                caloriesPerHundredGrams = CaloriesModel.NONE
                proteinsPerHundredGrams = ProteinsModel.NONE
                fatsPerHundredGrams = FatsModel.NONE
                carbohydratesPerHundredGrams = CarbohydratesModel.NONE
            }))
        }
        assertFalse(result.isSuccess)
        assertNull(result.result)
        assertEquals(5, result.errors.size)
    }

    companion object : BaseInitProduct() {
        val createObject = ProductModel(
            productName = "create object",
            caloriesPerHundredGrams = CaloriesModel(calories = 110.0),
            proteinsPerHundredGrams = ProteinsModel(proteins = 21.0),
            fatsPerHundredGrams = FatsModel(fats = 3.0),
            carbohydratesPerHundredGrams = CarbohydratesModel(carbohydrates = 0.0),
        )

        override val initObjects: List<ProductModel> = emptyList()
    }
}