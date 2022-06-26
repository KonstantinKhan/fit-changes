package ru.fit_changes.backend.ration.repo.inmemory

import ru.fit_changes.backend.common.models.*
import ru.fit_changes.backend.common.models.ration.RationIdModel
import ru.fit_changes.backend.common.models.ration.RationModel
import java.time.Instant

data class RationRow(
    val rationId: String? = null,
    val authorId: String? = null,
    val dateRation: String? = null,
    val caloriesNorm: Double? = null,
    val proteinsNorm: Double? = null,
    val fatsNorm: Double? = null,
    val carbohydratesNorm: Double? = null,
    val caloriesFact: Double? = null,
    val proteinsFact: Double? = null,
    val fatsFact: Double? = null,
    val carbohydratesFact: Double? = null,
    val meals: List<RepoInMemoryMeal> = mutableListOf()
) {
    constructor(internal: RationModel) : this(
        rationId = internal.rationId.takeIf { it != RationIdModel.NONE }?.asString(),
        authorId = internal.authorId.takeIf { it != AuthorIdModel.NONE }?.asString(),
        dateRation = internal.dateRation.toString(),
        caloriesNorm = internal.caloriesNorm.takeIf { it != CaloriesModel.NONE }?.value,
        proteinsNorm = internal.proteinsNorm.takeIf { it != ProteinsModel.NONE }?.value,
        fatsNorm = internal.fatsNorm.takeIf { it != FatsModel.NONE }?.value,
        carbohydratesNorm = internal.carbohydratesNorm.takeIf { it != CarbohydratesModel.NONE }?.value,
        caloriesFact = internal.caloriesFact.takeIf { it != CaloriesModel.NONE }?.value,
        proteinsFact = internal.proteinsFact.takeIf { it != ProteinsModel.NONE }?.value,
        fatsFact = internal.fatsFact.takeIf { it != FatsModel.NONE }?.value,
        carbohydratesFact = internal.carbohydratesFact.takeIf { it != CarbohydratesModel.NONE }?.value,
        meals = internal.meals.map {
            RepoInMemoryMeal(it)
        }
    )

    fun toInternal(): RationModel = RationModel(
        rationId = rationId?.let { RationIdModel(it) } ?: RationIdModel.NONE,
        authorId = authorId?.let { AuthorIdModel(it) } ?: AuthorIdModel.NONE,
        dateRation = Instant.parse(dateRation),
        caloriesNorm = caloriesNorm?.let { CaloriesModel(it) } ?: CaloriesModel.NONE,
        proteinsNorm = proteinsNorm?.let { ProteinsModel(it) } ?: ProteinsModel.NONE,
        fatsNorm = fatsNorm?.let { FatsModel(it) } ?: FatsModel.NONE,
        carbohydratesNorm = carbohydratesNorm?.let { CarbohydratesModel(it) } ?: CarbohydratesModel.NONE,
        caloriesFact = caloriesFact?.let { CaloriesModel(it) } ?: CaloriesModel.NONE,
        proteinsFact = proteinsFact?.let { ProteinsModel(it) } ?: ProteinsModel.NONE,
        fatsFact = fatsFact?.let { FatsModel(it) } ?: FatsModel.NONE,
        carbohydratesFact = carbohydratesFact?.let { CarbohydratesModel(it) } ?: CarbohydratesModel.NONE,
        meals = meals.map { it.toInternal() }.toMutableList()
    )
}
