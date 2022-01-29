package ru.fit_changes.backend.common.product.models

@JvmInline
value class ProductIdModel(private val id: String) {
    companion object {
        val NONE = ProductIdModel("")
    }

    fun asString() = toString()

    override fun toString(): String = id
}
