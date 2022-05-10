package ru.fit_changes.backend.common.context

import ru.fit_changes.backend.common.models.*
import ru.fit_changes.backend.common.models.enums.BeUserPermissions
import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.backend.common.product.models.ProductModel
import ru.fit_changes.backend.common.product.models.ProductSearchFilter
import ru.fit_changes.backend.repo.product.IRepoProduct

data class BeContext(
    var config: ContextConfig = ContextConfig(),

    var status: CorStatus = CorStatus.NONE,
    var productRepo: IRepoProduct = IRepoProduct.NONE,
    var stubCase: StubCases = StubCases.NONE,

    var requestId: String = "",
    var operation: Operations = Operations.NONE,
    var workMode: WorkMode = WorkMode.PROD,

    var requestProductId: ProductIdModel = ProductIdModel.NONE,
    var requestProduct: ProductModel = ProductModel(),
    var requestProductFilter: ProductSearchFilter = ProductSearchFilter(),

    var principal: BePrincipalModel = BePrincipalModel.NONE,
    val userPermissions: MutableSet<BeUserPermissions> = mutableSetOf(),
    var permitted: Boolean = false,

    var dbProduct: ProductModel = ProductModel(),

    var responseProduct: ProductModel = ProductModel(),
    var foundProducts: MutableList<ProductModel> = mutableListOf(),

    var errors: MutableSet<IError> = mutableSetOf()
) {
    fun addError(error: IError, failingStatus: Boolean = true) {
        if (failingStatus) status = CorStatus.FAILING
        errors.add(error)
    }

    fun addError(
        t: Throwable,
        level: IError.Level = IError.Level.ERROR,
    ) {
        addError(CommonErrorModel(t))
    }
}