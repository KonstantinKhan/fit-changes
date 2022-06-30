package ru.fit_changes.backend.repo.ration

interface IRepoRation {
    suspend fun create(req: DbRationModelRequest): DbRationResponse
    suspend fun read(req: DbRationIdRequest): DbRationResponse
    suspend fun update(req: DbRationModelRequest): DbRationResponse
    suspend fun delete(req: DbRationIdRequest): DbRationResponse
    suspend fun search(req: DbRationFilterRequest): DbRationsResponse

    companion object NONE : IRepoRation {
        override suspend fun create(req: DbRationModelRequest): DbRationResponse {
            TODO("Not yet implemented")
        }

        override suspend fun read(req: DbRationIdRequest): DbRationResponse {
            TODO("Not yet implemented")
        }

        override suspend fun update(req: DbRationModelRequest): DbRationResponse {
            TODO("Not yet implemented")
        }

        override suspend fun delete(req: DbRationIdRequest): DbRationResponse {
            TODO("Not yet implemented")
        }

        override suspend fun search(req: DbRationFilterRequest): DbRationsResponse {
            TODO("Not yet implemented")
        }
    }
}