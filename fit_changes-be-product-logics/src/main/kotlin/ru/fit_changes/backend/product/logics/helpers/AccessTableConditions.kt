package ru.fit_changes.backend.product.logics.helpers

import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.backend.common.models.enums.BePrincipalRelations
import ru.fit_changes.backend.common.models.enums.BeUserPermissions

data class AccessTableConditions(
    val operation: Operations,
    val permission: BeUserPermissions,
    val relation: BePrincipalRelations
)

val accessTable = mapOf(

    //CREATE
    AccessTableConditions(
        operation = Operations.CREATE,
        permission = BeUserPermissions.CREATE_OWN,
        relation = BePrincipalRelations.NONE
    ) to true,

    //READ
    AccessTableConditions(
        operation = Operations.READ,
        permission = BeUserPermissions.READ_OWN,
        relation = BePrincipalRelations.AUTHOR
    ) to true,
    AccessTableConditions(
        operation = Operations.READ,
        permission = BeUserPermissions.READ_PUBLIC,
        relation = BePrincipalRelations.PUBLIC
    ) to true,

    //UPDATE
    AccessTableConditions(
        operation = Operations.UPDATE,
        permission = BeUserPermissions.UPDATE_OWN,
        relation = BePrincipalRelations.AUTHOR
    ) to true,
    AccessTableConditions(
        operation = Operations.READ,
        permission = BeUserPermissions.READ_PUBLIC,
        relation = BePrincipalRelations.MODERATOR
    ) to true,

    //DELETE
    AccessTableConditions(
        operation = Operations.DELETE,
        permission = BeUserPermissions.DELETE_OWN,
        relation = BePrincipalRelations.AUTHOR
    ) to true,
    AccessTableConditions(
        operation = Operations.DELETE,
        permission = BeUserPermissions.DELETE_PUBLIC,
        relation = BePrincipalRelations.MODERATOR
    ) to true,

    //SEARCH
    AccessTableConditions(
        operation = Operations.SEARCH,
        permission = BeUserPermissions.SEARCH_OWN,
        relation = BePrincipalRelations.AUTHOR
    ) to true,
    AccessTableConditions(
        operation = Operations.SEARCH,
        permission = BeUserPermissions.SEARCH_PUBLIC,
        relation = BePrincipalRelations.PUBLIC
    ) to true,

)