package ru.fit_changes.backend.repo

import ru.fit_changes.backend.common.models.CommonErrorModel

interface IDbResponse<T> {
    val isSuccess: Boolean
    val errors: List<CommonErrorModel>
    val result: T?
}