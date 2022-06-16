package ru.fit_changes.backend.repo.product

import ru.fit_changes.backend.common.models.AuthorIdModel
import ru.fit_changes.backend.repo.IDbRequest

data class DbProductFilterRequest(
    val query: String = "",
    val authorId: AuthorIdModel = AuthorIdModel.NONE
): IDbRequest