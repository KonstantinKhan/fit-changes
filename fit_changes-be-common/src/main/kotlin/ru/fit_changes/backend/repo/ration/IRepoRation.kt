package ru.fit_changes.backend.repo.ration

interface IRepoRation {
    suspend fun create(req: DbRationModelRequest): DbRationResponse
    suspend fun read(req: DbRationIdRequest): DbRationResponse
    suspend fun update(req: DbRationModelRequest): DbRationResponse
    suspend fun delete(req: DbRationIdRequest): DbRationResponse
    suspend fun search(req: DbRationFilterRequest): DbRationsResponse
}