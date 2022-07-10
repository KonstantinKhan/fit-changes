import ru.fit_changes.backend.ration.repo.inmemory.RepoRationInMemory
import ru.fit_changes.backend.ration.repo.test.RepoRationUpdateTest
import ru.fit_changes.backend.repo.ration.IRepoRation

class RepoRationInmemoryUpdateTest : RepoRationUpdateTest() {
    override val repo: IRepoRation = RepoRationInMemory(initObjects)
}