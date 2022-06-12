package ru.fit_changes.backend.common.models.ration

import ru.fit_changes.backend.common.models.*
import java.time.Instant

data class RationModel(
    var authorId: AuthorIdModel = AuthorIdModel.NONE,
    var dateRation: Instant = Instant.now(),
    var caloriesNorm: CaloriesModel = CaloriesModel.NONE,
    var proteinsNorm: ProteinsModel = ProteinsModel.NONE,
    var fatsNorm: FatsModel = FatsModel.NONE,
    var carbohydratesNorm: CarbohydratesModel = CarbohydratesModel.NONE,
    var caloriesFact: CaloriesModel = CaloriesModel.NONE,
    var proteinsFact: ProteinsModel = ProteinsModel.NONE,
    var fatsFact: FatsModel = FatsModel.NONE,
    var carbohydratesFact: CarbohydratesModel = CarbohydratesModel.NONE,
    val meals: MutableList<BeMeal> = mutableListOf()
)