package ru.fit_changes.backend.common.product.models

import ru.fit_changes.backend.common.models.AuthorIdModel

data class ProductSearchFilter(
    var searchStr: String = "",
    var authorId: AuthorIdModel = AuthorIdModel.NONE
)