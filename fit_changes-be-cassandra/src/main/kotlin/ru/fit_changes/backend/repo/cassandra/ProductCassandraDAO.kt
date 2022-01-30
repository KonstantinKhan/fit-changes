package ru.fit_changes.backend.repo.cassandra

import com.datastax.oss.driver.api.mapper.annotations.Dao
import com.datastax.oss.driver.api.mapper.annotations.Insert
import com.datastax.oss.driver.api.mapper.annotations.Select
import java.util.concurrent.CompletionStage

@Dao
interface ProductCassandraDAO {
    @Insert
    fun create(dto: ProductCassandraDTO): CompletionStage<Void>

    @Select
    fun read(id: String): CompletionStage<ProductCassandraDTO?>
}