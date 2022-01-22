package common.context

import common.models.ProductModel

data class BeContext(
    var requestProduct: ProductModel = ProductModel()
)