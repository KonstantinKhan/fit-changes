package ru.fit_changes.backend.repo.cassandra

import com.datastax.oss.driver.api.mapper.annotations.Dao
import com.datastax.oss.driver.api.mapper.annotations.Delete
import com.datastax.oss.driver.api.mapper.annotations.Insert
import com.datastax.oss.driver.api.mapper.annotations.QueryProvider
import com.datastax.oss.driver.api.mapper.annotations.Select
import com.datastax.oss.driver.api.mapper.annotations.Update
import ru.fit_changes.backend.repo.product.DbProductFilterRequest
import java.util.concurrent.CompletionStage

@Dao
interface ProductCassandraDAO {
    @Insert
    fun create(dto: ProductCassandraDTO): CompletionStage<Void>

    @Select
    fun read(id: String): CompletionStage<ProductCassandraDTO?>

    @Update(ifExists = true)
    fun update(dto: ProductCassandraDTO): CompletionStage<Void>

    @Delete
    fun delete(dto: ProductCassandraDTO): CompletionStage<Void>

    @QueryProvider(
        providerClass = ProductCassandraSearchProvider::class,
        entityHelpers = [ProductCassandraDTO::class]
    )
    fun search(req: DbProductFilterRequest): CompletionStage<Collection<ProductCassandraDTO>>
}