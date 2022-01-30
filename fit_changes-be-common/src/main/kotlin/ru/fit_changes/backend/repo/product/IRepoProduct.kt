package ru.fit_changes.backend.repo.product

interface IRepoProduct {
    suspend fun create(req: DbProductModelRequest): DbProductResponse
    suspend fun read(req: DbProductIdRequest): DbProductResponse
    suspend fun update(req: DbProductModelRequest): DbProductResponse
    suspend fun delete(req: DbProductIdRequest): DbProductResponse
    suspend fun search(req: DbProductFilterRequest): DbProductsResponse
}