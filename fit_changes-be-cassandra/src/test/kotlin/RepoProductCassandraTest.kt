import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import com.datastax.oss.driver.internal.core.util.concurrent.CompletableFutures
import org.testcontainers.containers.CassandraContainer
import ru.fit_changes.backend.common.product.models.ProductModel
import ru.fit_changes.backend.repo.cassandra.ProductCassandraDTO
import ru.fit_changes.backend.repo.cassandra.ProductCassandraMapper
import ru.fit_changes.backend.repo.cassandra.ProductCassandraMapperBuilder
import ru.fit_changes.backend.repo.cassandra.RepoProductCassandra
import ru.fit_changes.backend.repo.product.IRepoProduct
import ru.fit_changes.backend.repo.test.*
import java.net.InetSocketAddress

class RepoProductCassandraCreateTest : RepoProductCreateTest() {
    override val repo: IRepoProduct = TestComponent.createRepo(initObjects)
}

class RepoProductCassandraReadTest : RepoProductReadTest() {
    override val repo: IRepoProduct = TestComponent.createRepo(initObjects)
}

class RepoProductCassandraUpdateTest : RepoProductUpdateTest() {
    override val repo: IRepoProduct = TestComponent.createRepo(initObjects)
}

class RepoProductCassandraSearchTest : RepoProductSearchTest() {
    override val repo: IRepoProduct = TestComponent.createRepo(initObjects)
}

class RepoProductCassandraDeleteTest : RepoProductDeleteTest() {
    override val repo: IRepoProduct = TestComponent.createRepo(initObjects)
}

class TestCassandraContainer : CassandraContainer<TestCassandraContainer>("cassandra:3.11.2")

object TestComponent {

    private val container by lazy { TestCassandraContainer().apply { start() } }
    private val session by lazy {
        CqlSession.builder()
            .addContactPoint(InetSocketAddress(container.host, container.getMappedPort(CassandraContainer.CQL_PORT)))
            .withLocalDatacenter("datacenter1")
            .withAuthCredentials(container.username, container.password)
            .build()
    }
    private val mapper: ProductCassandraMapper by lazy {
        ProductCassandraMapperBuilder(session).build()
    }

    fun createRepo(initObjects: List<ProductModel>): RepoProductCassandra {
        println("createRepo()")
        val keyspace = "data"
        val tableName = ProductCassandraDTO.TABLE_NAME
        session.execute(
            SchemaBuilder
                .createKeyspace(keyspace)
                .ifNotExists()
                .withSimpleStrategy(1)
                .build()
        )
        session.execute(ProductCassandraDTO.table(keyspace, ProductCassandraDTO.TABLE_NAME))
        session.execute(ProductCassandraDTO.productNameIndex(keyspace, ProductCassandraDTO.TABLE_NAME))
        val dao = mapper.productCassandraDao(keyspace, ProductCassandraDTO.TABLE_NAME)
        CompletableFutures
            .allDone(initObjects.map { dao.create(ProductCassandraDTO(it)) })
            .toCompletableFuture()
            .get()
        return RepoProductCassandra(dao)
    }
}