import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import org.testcontainers.containers.CassandraContainer
import ru.fit_changes.backend.repo.cassandra.ProductCassandraDTO
import ru.fit_changes.backend.repo.cassandra.ProductCassandraMapper
import ru.fit_changes.backend.repo.cassandra.ProductCassandraMapperBuilder
import ru.fit_changes.backend.repo.cassandra.RepoProductCassandra
import ru.fit_changes.backend.repo.product.IRepoProduct
import ru.fit_changes.backend.repo.test.RepoProductCreateTest
import java.net.InetSocketAddress

class RepoProductCassandraCreateTest : RepoProductCreateTest() {
    override val repo: IRepoProduct = TestComponent.createRepo()

}

class TestCassandraContainer : CassandraContainer<TestCassandraContainer>("cassandra:latest")

object TestComponent {

    private val container by lazy { TestCassandraContainer().apply { start() } }
    private val session: CqlSession by lazy {
        CqlSession.builder()
            .addContactPoint(InetSocketAddress(container.host, container.getMappedPort(CassandraContainer.CQL_PORT)))
            .withLocalDatacenter("datacenter1")
            .withAuthCredentials(container.username, container.password)
            .build()
    }
    private val mapper: ProductCassandraMapper by lazy {
        ProductCassandraMapperBuilder(session).build()
    }

    fun createRepo(): RepoProductCassandra {
        val keyspace = "data"
        val tableName = ProductCassandraDTO.TABLE_NAME
        session.execute(
            SchemaBuilder
                .createKeyspace(keyspace)
                .ifNotExists()
                .withSimpleStrategy(1)
                .build()
        )
        session.execute(ProductCassandraDTO.table(keyspace, tableName))
        val dao = mapper.productCassandraDao(keyspace, tableName)
        return RepoProductCassandra(dao)
    }
}