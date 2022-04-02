package ru.fit_changes.backend.repo.inmemory

import ru.fit_changes.backend.common.product.models.*
import java.io.Serializable

data class ProductRow(
    val productId: String? = null,
    val authorId: String? = null,
    val category: String? = null,
    val productName: String? = null,
    val caloriesPerHundredGrams: Double? = null,
    val proteinsPerHundredGrams: Double? = null,
    val fatsPerHundredGrams: Double? = null,
    val carbohydratesPerHundredGrams: Double? = null
) : Serializable {
    constructor(internal: ProductModel) : this(
        productId = internal.productId.takeIf { it != ProductIdModel.NONE }?.asString(),
        authorId = internal.authorId.takeIf { it != AuthorIdModel.NONE }?.asString(),
        productName = internal.productName.takeIf { it.isNotBlank() },
        caloriesPerHundredGrams = internal.caloriesPerHundredGrams.takeIf { it != CaloriesModel.NONE }?.value,
        proteinsPerHundredGrams = internal.proteinsPerHundredGrams.takeIf { it != ProteinsModel.NONE }?.value,
        fatsPerHundredGrams = internal.fatsPerHundredGrams.takeIf { it != FatsModel.NONE }?.value,
        carbohydratesPerHundredGrams = internal.carbohydratesPerHundredGrams.takeIf { it != CarbohydratesModel.NONE }?.value,
    )

    fun toInternal(): ProductModel = ProductModel(
        productId = productId?.let { ProductIdModel(it) } ?: ProductIdModel.NONE,
        authorId = authorId?.let { AuthorIdModel(it) } ?: AuthorIdModel.NONE,
        productName = productName ?: "",
        caloriesPerHundredGrams = caloriesPerHundredGrams?.let { CaloriesModel(it) } ?: CaloriesModel.NONE,
        proteinsPerHundredGrams = proteinsPerHundredGrams?.let { ProteinsModel(it) } ?: ProteinsModel.NONE,
        fatsPerHundredGrams = fatsPerHundredGrams?.let { FatsModel(it) } ?: FatsModel.NONE,
        carbohydratesPerHundredGrams = carbohydratesPerHundredGrams?.let { CarbohydratesModel(it) }
            ?: CarbohydratesModel.NONE
    )
}