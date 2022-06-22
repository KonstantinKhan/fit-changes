package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.models.enums.BeUserGroups
import ru.fit_changes.backend.common.product.models.ProductPermissions
import ru.fit_changes.cor.ICorChain
import ru.fit_changes.cor.addCorWorkerDsl
import ru.fit_changes.cor.chain

fun ICorChain<BeContext>.frontPermissions(title: String) = chain {
    this.title = title
    on {
        status == CorStatus.RUNNING
    }
    addCorWorkerDsl {
        on {
            responseProduct.authorId == principal.authorId
        }
        handle {
            val addPermissions: Set<ProductPermissions> = principal.groups.map {
                when (it) {
                    BeUserGroups.USER -> setOf(
                        ProductPermissions.READ,
                        ProductPermissions.UPDATE,
                        ProductPermissions.DELETE,
                    )
                    BeUserGroups.ADMIN -> setOf()
                    BeUserGroups.MODERATOR -> setOf()
                    BeUserGroups.TEST -> setOf()
                    BeUserGroups.BAN -> setOf()
                }
            }.flatten().toSet()

            val deletePermissions: Set<ProductPermissions> = principal.groups.map {
                when (it) {
                    BeUserGroups.USER -> setOf()
                    BeUserGroups.ADMIN -> setOf()
                    BeUserGroups.MODERATOR -> setOf()
                    BeUserGroups.TEST -> setOf()
                    BeUserGroups.BAN -> setOf(
                        ProductPermissions.UPDATE,
                        ProductPermissions.DELETE
                    )
                }
            }.flatten().toSet()

            responseProduct.permissions.addAll(addPermissions)
            responseProduct.permissions.removeAll(deletePermissions)
        }
    }

    addCorWorkerDsl {
        on {
            responseProduct.authorId != principal.authorId
        }
        handle {
            val addPermissions: Set<ProductPermissions> = principal.groups.map {
                when (it) {
                    BeUserGroups.USER -> setOf(
                        ProductPermissions.READ,
                    )
                    BeUserGroups.ADMIN -> setOf(
                        ProductPermissions.READ,
                        ProductPermissions.UPDATE,
                        ProductPermissions.DELETE
                    )
                    BeUserGroups.MODERATOR -> setOf(
                        ProductPermissions.READ,
                        ProductPermissions.UPDATE,
                        ProductPermissions.DELETE,
                    )
                    BeUserGroups.TEST -> setOf()
                    BeUserGroups.BAN -> setOf()
                }
            }.flatten().toSet()

            val deletePermissions: Set<ProductPermissions> = principal.groups.map {
                when (it) {
                    BeUserGroups.USER -> setOf()
                    BeUserGroups.ADMIN -> setOf()
                    BeUserGroups.TEST -> setOf()
                    BeUserGroups.MODERATOR -> setOf()
                    BeUserGroups.BAN -> setOf(
                        ProductPermissions.UPDATE,
                        ProductPermissions.DELETE
                    )
                }
            }.flatten().toSet()

            responseProduct.permissions.addAll(addPermissions)
            responseProduct.permissions.removeAll(deletePermissions)
        }
    }
}