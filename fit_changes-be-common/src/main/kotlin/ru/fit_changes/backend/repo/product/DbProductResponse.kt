package ru.fit_changes.backend.repo.product

import ru.fit_changes.backend.common.product.models.ProductModel
import ru.fit_changes.backend.repo.IDbResponse

data class DbProductResponse(
    override val result: ProductModel?

) : IDbResponse<ProductModel?> {

}