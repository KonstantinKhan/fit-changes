package ru.fit_changes.backend.common.models.ration

import java.util.*

@JvmInline
value class RationIdModel(private val id: String) {

    constructor(id: UUID) : this(id.toString())

    companion object {
        val NONE = RationIdModel("")
    }

    fun asString() = toString()

    override fun toString(): String = id
}