package ru.fit_changes.backend.common.models

interface IError {
    val field: String
    val level: Level
    val message: String

    enum class Level {
        ERROR,
        WARNING
    }
}
