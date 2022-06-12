package ru.fit_changes.backend.common.models

import java.util.UUID

@JvmInline
value class AuthorIdModel(private val id: String) {
    constructor(id: UUID) : this(id.toString())

    companion object {
        val NONE = AuthorIdModel("")
    }

    fun asString() = toString()

    override fun toString(): String = id
}