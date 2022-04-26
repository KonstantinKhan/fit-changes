package ru.fit_changes.backend.repo.cassandra

import com.datastax.oss.driver.api.mapper.annotations.Dao
import com.datastax.oss.driver.api.mapper.annotations.Insert
import com.datastax.oss.driver.api.mapper.annotations.QueryProvider
import com.datastax.oss.driver.api.mapper.annotations.Select
import ru.fit_changes.backend.repo.product.DbProductFilterRequest
import java.util.concurrent.CompletionStage

@Dao
interface ProductCassandraDAO {
    @Insert
    fun create(dto: ProductCassandraDTO): CompletionStage<Void>

    @Select
    fun read(id: String): CompletionStage<ProductCassandraDTO?>

    @QueryProvider(
        providerClass = ProductCassandraSearchProvider::class,
        entityHelpers = [ProductCassandraDTO::class]
    )
    fun search(req: DbProductFilterRequest): CompletionStage<Collection<ProductCassandraDTO>>
}