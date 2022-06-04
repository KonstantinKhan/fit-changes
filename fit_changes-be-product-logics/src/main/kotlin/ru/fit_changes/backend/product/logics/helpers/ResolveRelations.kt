package ru.fit_changes.backend.product.logics.helpers

import ru.fit_changes.backend.common.models.BePrincipalModel
import ru.fit_changes.backend.common.models.enums.BePrincipalRelations
import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.backend.common.product.models.ProductModel

fun ProductModel.resolveRelationsTo(principal: BePrincipalModel) = listOfNotNull(
    BePrincipalRelations.NONE.takeIf { productId == ProductIdModel.NONE },
    BePrincipalRelations.AUTHOR.takeIf { principal.authorId == authorId },
    BePrincipalRelations.PUBLIC.takeIf { principal.authorId != authorId }
).toSet()