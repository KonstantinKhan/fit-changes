package ru.fit_changes.backend.repo.product

interface IRepoProduct {
    suspend fun create(req: DbProductModelRequest): DbProductResponse
    suspend fun read(req: DbProductIdRequest): DbProductResponse
    suspend fun update(req: DbProductModelRequest): DbProductResponse
    suspend fun delete(req: DbProductIdRequest): DbProductResponse
    suspend fun search(req: DbProductFilterRequest): DbProductsResponse

    object NONE: IRepoProduct {
        override suspend fun create(req: DbProductModelRequest): DbProductResponse {
            TODO("Not yet implemented")
        }

        override suspend fun read(req: DbProductIdRequest): DbProductResponse {
            TODO("Not yet implemented")
        }

        override suspend fun update(req: DbProductModelRequest): DbProductResponse {
            TODO("Not yet implemented")
        }

        override suspend fun delete(req: DbProductIdRequest): DbProductResponse {
            TODO("Not yet implemented")
        }

        override suspend fun search(req: DbProductFilterRequest): DbProductsResponse {
            TODO("Not yet implemented")
        }
    }
}