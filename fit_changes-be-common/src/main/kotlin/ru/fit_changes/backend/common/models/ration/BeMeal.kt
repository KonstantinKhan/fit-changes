package ru.fit_changes.backend.common.models.ration

import ru.fit_changes.backend.common.models.enums.BeMealName

data class BeMeal(
    val mealName: BeMealName,
    val usedProducts: MutableList<BeUsedProduct> = mutableListOf()
)
