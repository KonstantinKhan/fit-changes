package ru.fit_changes.backend.repo.product

import ru.fit_changes.backend.common.product.models.ProductModel
import ru.fit_changes.backend.repo.IDbRequest

class DbProductModelRequest(
    val product: ProductModel
) : IDbRequest