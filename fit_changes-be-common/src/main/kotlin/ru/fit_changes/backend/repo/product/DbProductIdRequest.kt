package ru.fit_changes.backend.repo.product

import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.backend.repo.IDbRequest

class DbProductIdRequest(
    val id: ProductIdModel
): IDbRequest