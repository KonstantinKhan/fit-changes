package ru.fit_changes.backend.repo.inmemory

import ru.fit_changes.backend.common.product.models.AuthorIdModel
import ru.fit_changes.backend.common.product.models.CaloriesModel
import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.backend.common.product.models.ProductModel
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
        proteinsPerHundredGrams = internal.proteinsPerHundredGrams,
        fatsPerHundredGrams = internal.fatsPerHundredGrams,
        carbohydratesPerHundredGrams = internal.carbohydratesPerHundredGrams
    )

    fun toInternal(): ProductModel = ProductModel(
        productId = productId?.let { ProductIdModel(it) } ?: ProductIdModel.NONE,
        authorId = authorId?.let { AuthorIdModel(it) } ?: AuthorIdModel.NONE,
        productName = productName ?: "",
        caloriesPerHundredGrams = caloriesPerHundredGrams?.let { CaloriesModel(it) } ?: CaloriesModel.NONE,
        proteinsPerHundredGrams = proteinsPerHundredGrams ?: 0.0,
        fatsPerHundredGrams = fatsPerHundredGrams ?: 0.0,
        carbohydratesPerHundredGrams = carbohydratesPerHundredGrams ?: 0.0
    )
}