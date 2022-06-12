package ru.fit_changes.backend.common.models

@JvmInline
value class ProteinsModel(private val proteins: Double) {
    val value: Double
        get() = proteins

    companion object {
        val NONE = ProteinsModel(Double.NaN)
    }
}