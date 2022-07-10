import ru.fit_changes.backend.ration.repo.inmemory.RepoRationInMemory
import ru.fit_changes.backend.ration.repo.test.RepoRationCreateTest
import ru.fit_changes.backend.repo.ration.IRepoRation

class RepoRationInMemoryCreateTest: RepoRationCreateTest() {
    override val repo: IRepoRation = RepoRationInMemory()
}