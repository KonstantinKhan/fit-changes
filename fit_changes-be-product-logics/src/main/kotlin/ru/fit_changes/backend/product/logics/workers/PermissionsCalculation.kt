package ru.fit_changes.backend.product.logics.workers

import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.models.enums.BeUserGroups
import ru.fit_changes.backend.common.models.enums.BeUserPermissions
import ru.fit_changes.cor.ICorChain
import ru.fit_changes.cor.worker

fun ICorChain<BeContext>.permissionsCalculation(title: String) = worker {
    this.title = title
    description = "Permissions calculation for user"
    on {
        status == CorStatus.RUNNING
    }
    handle {
        val permissionsAdd: Set<BeUserPermissions> = principal.groups.map {
            when (it) {
                BeUserGroups.USER -> setOf(
                    BeUserPermissions.CREATE_OWN,
                    BeUserPermissions.READ_OWN,
                    BeUserPermissions.READ_PUBLIC,
                    BeUserPermissions.UPDATE_OWN,
                    BeUserPermissions.DELETE_OWN,
                    BeUserPermissions.SEARCH_OWN,
                    BeUserPermissions.SEARCH_PUBLIC
                )
                BeUserGroups.ADMIN -> setOf(
                    BeUserPermissions.CREATE_OWN,
                    BeUserPermissions.CREATE_PUBLIC,
                    BeUserPermissions.READ_OWN,
                    BeUserPermissions.READ_PUBLIC,
                    BeUserPermissions.UPDATE_OWN,
                    BeUserPermissions.UPDATE_PUBLIC,
                    BeUserPermissions.DELETE_OWN,
                    BeUserPermissions.DELETE_PUBLIC,
                    BeUserPermissions.SEARCH_OWN,
                    BeUserPermissions.SEARCH_PUBLIC
                )
                BeUserGroups.TEST -> setOf()
                BeUserGroups.MODERATOR -> setOf(
                    BeUserPermissions.CREATE_OWN,
                    BeUserPermissions.CREATE_PUBLIC,
                    BeUserPermissions.READ_OWN,
                    BeUserPermissions.READ_PUBLIC,
                    BeUserPermissions.UPDATE_OWN,
                    BeUserPermissions.UPDATE_PUBLIC,
                    BeUserPermissions.DELETE_OWN,
                    BeUserPermissions.SEARCH_OWN,
                    BeUserPermissions.SEARCH_PUBLIC
                )
                BeUserGroups.BAN -> setOf()
            }
        }.flatten().toSet()

        val permissionsDelete: Set<BeUserPermissions> = principal.groups.map {
            when (it) {
                BeUserGroups.USER -> setOf()
                BeUserGroups.ADMIN -> setOf()
                BeUserGroups.TEST -> setOf()
                BeUserGroups.MODERATOR -> setOf()
                BeUserGroups.BAN -> setOf(
                    BeUserPermissions.CREATE_OWN,
                    BeUserPermissions.CREATE_PUBLIC,
                    BeUserPermissions.UPDATE_OWN,
                    BeUserPermissions.UPDATE_PUBLIC,
                    BeUserPermissions.DELETE_OWN,
                    BeUserPermissions.DELETE_PUBLIC
                )
            }
        }.flatten().toSet()

        userPermissions.addAll(permissionsAdd)
        userPermissions.removeAll(permissionsDelete)
    }
}