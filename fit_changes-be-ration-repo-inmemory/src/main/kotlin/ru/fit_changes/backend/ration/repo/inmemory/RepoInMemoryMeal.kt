package ru.fit_changes.backend.ration.repo.inmemory

import ru.fit_changes.backend.common.models.enums.BeMealName
import ru.fit_changes.backend.common.models.ration.BeMeal

data class RepoInMemoryMeal(
    val mealName: String? = null,
    val products: List<RepoInMemoryUsedProduct> = mutableListOf()
) {
    constructor(internal: BeMeal) : this(
        mealName = internal.mealName.name,
        products = internal.usedProducts.map {
            RepoInMemoryUsedProduct(it)
        }
    )

    fun toInternal() = BeMeal(
        mealName = mealName?.let { BeMealName.valueOf(it) } ?: BeMealName.NONE,
        usedProducts = products.map { it.toInternal() }.toMutableList()
    )
}