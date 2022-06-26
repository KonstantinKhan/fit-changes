package ru.fit_changes.backend.repo.ration

interface IRepoRation {
    suspend fun create(req: DbRationModelRequest): DbRationResponse
}