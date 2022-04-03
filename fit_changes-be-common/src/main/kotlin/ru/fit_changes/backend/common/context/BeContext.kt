package ru.fit_changes.backend.common.context

import ru.fit_changes.backend.common.models.StubCases
import ru.fit_changes.backend.common.models.IError
import ru.fit_changes.backend.common.models.WorkMode
import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.backend.common.product.models.ProductModel
import ru.fit_changes.backend.repo.product.IRepoProduct

data class BeContext(

    var config: ContextConfig = ContextConfig(),

    var requestId: String = "",
    var operation: Operations = Operations.NONE,
    var workMode: WorkMode = WorkMode.PROD,
    var stubCase: StubCases = StubCases.NONE,
    var status: CorStatus = CorStatus.NONE,

    var requestProductId: ProductIdModel = ProductIdModel.NONE,

    var dbProduct: ProductModel = ProductModel(),
    var requestProduct: ProductModel = ProductModel(),
    var responseProduct: ProductModel = ProductModel(),

    var requestQuery: String = "",

    var productRepo: IRepoProduct = IRepoProduct.NONE,

    var errors: MutableSet<IError> = mutableSetOf()
) {

    fun addError(error: IError, failingStatus: Boolean = true) {
        if (failingStatus) status = CorStatus.FAILING
        errors.add(error)
    }
}