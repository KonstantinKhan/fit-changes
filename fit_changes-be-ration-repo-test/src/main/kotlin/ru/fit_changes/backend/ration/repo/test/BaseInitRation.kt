package ru.fit_changes.backend.ration.repo.test

import ru.fit_changes.backend.common.models.*
import ru.fit_changes.backend.common.models.enums.BeMealName
import ru.fit_changes.backend.common.models.ration.BeMeal
import ru.fit_changes.backend.common.models.ration.RationIdModel
import ru.fit_changes.backend.common.models.ration.RationModel
import ru.fit_changes.backend.utils.product.USED_CHICKEN_PRODUCT
import java.time.Instant
import java.util.*

abstract class BaseInitRation : IInitObjects<RationModel> {
    fun createInitTestModel(
        suf: String
    ) = RationModel(
        rationId = RationIdModel(UUID.randomUUID()),
        authorId = AuthorIdModel(UUID.randomUUID()),
        dateRation = Instant.now(),
        caloriesNorm = CaloriesModel(calories = 2500.0),
        proteinsNorm = ProteinsModel(proteins = 140.0),
        fatsNorm = FatsModel(fats = 100.0),
        carbohydratesNorm = CarbohydratesModel(carbohydrates = 300.0),
        caloriesFact = CaloriesModel(calories = 2400.0),
        proteinsFact = ProteinsModel(proteins = 150.0),
        fatsFact = FatsModel(fats = 80.0),
        carbohydratesFact = CarbohydratesModel(carbohydrates = 400.0),
        meals = mutableListOf(
            BeMeal(
                mealName = BeMealName.LUNCH,
                usedProducts = mutableListOf(
                    USED_CHICKEN_PRODUCT
                )
            )
        ),
    )
}