package ru.fit_changes.backend.repo.cassandra

import com.datastax.oss.driver.api.core.cql.AsyncResultSet
import com.datastax.oss.driver.api.mapper.MapperContext
import com.datastax.oss.driver.api.mapper.entity.EntityHelper
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import ru.fit_changes.backend.repo.product.DbProductFilterRequest
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.function.BiConsumer

class ProductCassandraSearchProvider(
    private val context: MapperContext,
    private val entityHelper: EntityHelper<ProductCassandraDTO>
) {

    fun search(
        req: DbProductFilterRequest
    ): CompletionStage<Collection<ProductCassandraDTO>> {
        var select = entityHelper.selectStart().allowFiltering()
        if (req.query.isNotBlank()) {
            select = select.whereColumn(ProductCassandraDTO.COLUMN_PRODUCT_NAME)
                .like(QueryBuilder.literal("%${req.query}%"))
        }
        println("select: $select")

        val fetcher = CollectionFetcher()

        context.session
            .executeAsync(select.build())
            .whenComplete(fetcher)

        return fetcher.resultStage
    }

    inner class CollectionFetcher : BiConsumer<AsyncResultSet?, Throwable?> {
        private val buffer = mutableListOf<ProductCassandraDTO>()
        private val resultFuture = CompletableFuture<Collection<ProductCassandraDTO>>()
        val resultStage: CompletionStage<Collection<ProductCassandraDTO>> = resultFuture

        override fun accept(resultSet: AsyncResultSet?, t: Throwable?) {
            when {
                t != null -> resultFuture.completeExceptionally(t)
                resultSet == null -> resultFuture.completeExceptionally(IllegalStateException("Result set is null"))
                else -> {
                    buffer.addAll(resultSet.currentPage().map { entityHelper.get(it, false) })
                    if (resultSet.hasMorePages()) {
                        resultSet.fetchNextPage().whenComplete(this)
                    } else {
                        resultFuture.complete(buffer)
                    }
                }
            }
        }
    }
}