package ru.fit_changes.backend.common.models.ration

import ru.fit_changes.backend.common.models.AuthorIdModel

data class RationSearchFilter(
    var searchString: String = "",
    var authorId: AuthorIdModel = AuthorIdModel.NONE
)