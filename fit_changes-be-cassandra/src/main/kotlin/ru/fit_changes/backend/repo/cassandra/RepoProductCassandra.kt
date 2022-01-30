package ru.fit_changes.backend.repo.cassandra

import kotlinx.coroutines.future.await
import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.backend.repo.product.*
import java.util.*

class RepoProductCassandra(
    private val dao: ProductCassandraDAO
) : IRepoProduct {
    override suspend fun create(req: DbProductModelRequest): DbProductResponse {
        val product = req.product.copy(productId = ProductIdModel(UUID.randomUUID()))
        dao.create(ProductCassandraDTO(product)).await()
        val created = dao.read(product.productId.asString()).await()
        return DbProductResponse(created?.toProductModel())

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