package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.models.CommonErrorModel
import ru.fit_changes.cor.ICorChain
import ru.fit_changes.cor.addCorWorkerDsl
import ru.fit_changes.cor.chain
import ru.fit_changes.cor.worker
import ru.fit_changes.backend.product.logics.helpers.AccessTableConditions
import ru.fit_changes.backend.product.logics.helpers.accessTable
import ru.fit_changes.backend.product.logics.helpers.resolveRelationsTo

fun ICorChain<BeContext>.accessCalculation(title: String) = chain {
    this.title = title
    description = "Access calculation"
    on {
        status == CorStatus.RUNNING
    }
    worker("Relation calculation") {
        dbProduct.principalRelations = dbProduct.resolveRelationsTo(principal)
    }
    worker("Access calculation") {
        permitted = dbProduct.principalRelations.flatMap { relation ->
            userPermissions.map { permission ->
                AccessTableConditions(
                    operation = operation,
                    permission = permission,
                    relation = relation
                )
            }
        }
            .any {
                accessTable[it] ?: false
            }
    }
    addCorWorkerDsl {
        this.title = "Access validation"
        on {
            !permitted
        }
        handle {
            status = CorStatus.FAILING
            addError(
                CommonErrorModel(message = "User is not allowed to this operation")
            )
        }
    }
}