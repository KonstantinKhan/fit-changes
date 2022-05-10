package ru.fit_changes.backend.common.models.enums

enum class BeUserPermissions {
    CREATE_OWN,
    CREATE_PUBLIC,

    READ_OWN,
    READ_PUBLIC,

    UPDATE_OWN,
    UPDATE_PUBLIC,

    DELETE_OWN,
    DELETE_PUBLIC,

    SEARCH_OWN,
    SEARCH_PUBLIC
}