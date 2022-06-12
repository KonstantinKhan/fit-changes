package ru.fit_changes.backend.common.models.ration

data class BeMeal(
    val mealName: String,
    val usedProducts: MutableList<BeUsedProduct> = mutableListOf()
)
