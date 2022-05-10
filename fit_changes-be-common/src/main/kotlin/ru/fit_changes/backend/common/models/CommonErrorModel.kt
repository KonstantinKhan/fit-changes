package ru.fit_changes.backend.common.models

data class CommonErrorModel(
    override var field: String = "",
    override var level: IError.Level = IError.Level.ERROR,
    override var message: String = "",
) : IError {
    constructor(t: Throwable, level: IError.Level = IError.Level.ERROR, field: String = "") : this(
        field = field,
        level = level,
        message = t.message ?: ""
    )
}