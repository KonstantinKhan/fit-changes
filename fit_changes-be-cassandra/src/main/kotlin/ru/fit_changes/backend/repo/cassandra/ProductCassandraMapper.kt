package ru.fit_changes.backend.repo.cassandra

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace
import com.datastax.oss.driver.api.mapper.annotations.DaoTable
import com.datastax.oss.driver.api.mapper.annotations.Mapper

@Mapper
interface ProductCassandraMapper {
    @DaoFactory
    fun productCassandraDao(
        @DaoKeyspace keyspace: String,
        @DaoTable tableName: String
    ): ProductCassandraDAO
}