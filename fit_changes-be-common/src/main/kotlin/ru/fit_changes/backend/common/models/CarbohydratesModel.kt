package ru.fit_changes.backend.common.models

@JvmInline
value class CarbohydratesModel(private val carbohydrates: Double) {
    val value: Double
        get() = carbohydrates

    companion object {
        val NONE = CarbohydratesModel(Double.NaN)
    }
}