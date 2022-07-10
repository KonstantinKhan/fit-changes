package ru.fit_changes.backend.repo.ration

import ru.fit_changes.backend.common.models.AuthorIdModel
import ru.fit_changes.backend.repo.IDbRequest

data class DbRationFilterRequest(
    val query: String = "",
    val authorId: AuthorIdModel = AuthorIdModel.NONE
) : IDbRequest