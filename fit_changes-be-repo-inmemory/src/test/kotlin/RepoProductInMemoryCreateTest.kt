import ru.fit_changes.backend.repo.inmemory.RepoProductInMemory
import ru.fit_changes.backend.repo.product.IRepoProduct
import ru.fit_changes.backend.repo.test.RepoProductCreateTest

class RepoProductInMemoryCreateTest: RepoProductCreateTest() {
    override val repo: IRepoProduct = RepoProductInMemory(initObjects = initObjects)
}