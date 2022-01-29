package ru.fit_changes.backend.common.models

data class CommonError(
    override var field: String = "",
    override var level: IError.Level = IError.Level.ERROR,
    override var message: String = "",
) : IError