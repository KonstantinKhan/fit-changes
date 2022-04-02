package ru.fit_changes.backend.common.product.models

@JvmInline
value class FatsModel(private val fats: Double) {
    val value: Double
        get() = fats

    companion object {
        val NONE = FatsModel(Double.NaN)
    }
}