package ru.fit_changes.backend.common.product.models

@JvmInline
value class ProteinsModel(private val proteins: Double) {
    val value: Double
        get() = proteins

    companion object {
        val NONE = ProteinsModel(0.0)
    }
}