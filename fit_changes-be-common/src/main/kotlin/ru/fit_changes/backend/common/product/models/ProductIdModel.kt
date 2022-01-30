package ru.fit_changes.backend.common.product.models

import java.util.*

@JvmInline
value class ProductIdModel(private val id: String) {

    constructor(id: UUID) : this(id.toString())

    companion object {
        val NONE = ProductIdModel("")
    }

    fun asString() = toString()

    override fun toString(): String = id
}
