package ru.fit_changes.backend.common.product.models

@JvmInline
value class CaloriesModel(private val calories: Double) {
    val value: Double
        get() = calories

    companion object {
        val NONE = CaloriesModel(Double.NaN)
    }
}