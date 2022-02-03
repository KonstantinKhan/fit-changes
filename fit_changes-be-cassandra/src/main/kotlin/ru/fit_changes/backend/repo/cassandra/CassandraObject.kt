package ru.fit_changes.backend.repo.cassandra

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import java.net.InetSocketAddress

object CassandraObject {
    private val session by lazy {
        CqlSession.builder()
            .addContactPoint(InetSocketAddress("cassandra", 9042))
            .withLocalDatacenter("datacenter1")
            .withAuthCredentials("cassandra", "cassandra")
            .build()
    }

    val mapper: ProductCassandraMapper by lazy {
        ProductCassandraMapperBuilder(session).build()
    }

    fun createRepo(): RepoProductCassandra {
        val keyspace = "product_data"
        val tableName = ProductCassandraDTO.TABLE_NAME
        session.execute(
            SchemaBuilder
                .createKeyspace(keyspace)
                .ifNotExists()
                .withSimpleStrategy(1)
                .build()
        )
        session.execute(ProductCassandraDTO.table(keyspace, tableName))
//        session.execute(ProductCassandraDTO.productNameIndex(keyspace, tableName))
        val dao = mapper.productCassandraDao(keyspace, tableName)

        return RepoProductCassandra(dao)
    }
}