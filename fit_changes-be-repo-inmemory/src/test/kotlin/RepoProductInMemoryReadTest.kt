import ru.fit_changes.backend.repo.inmemory.RepoProductInMemory
import ru.fit_changes.backend.repo.product.IRepoProduct
import ru.fit_changes.backend.repo.test.RepoProductReadTest

class RepoProductInMemoryReadTest : RepoProductReadTest() {
    override val repo: IRepoProduct = RepoProductInMemory(initObjects)
}