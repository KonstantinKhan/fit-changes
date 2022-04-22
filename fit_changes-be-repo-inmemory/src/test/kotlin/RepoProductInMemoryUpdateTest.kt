import ru.fit_changes.backend.repo.inmemory.RepoProductInMemory
import ru.fit_changes.backend.repo.product.IRepoProduct
import ru.fit_changes.backend.repo.test.RepoProductUpdateTest

class RepoProductInMemoryUpdateTest : RepoProductUpdateTest() {
    override val repo: IRepoProduct = RepoProductInMemory(initObjects)
}