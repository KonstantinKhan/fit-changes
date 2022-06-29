import ru.fit_changes.backend.ration.repo.inmemory.RepoRationInMemory
import ru.fit_changes.backend.ration.repo.test.RepoRationDeleteTest
import ru.fit_changes.backend.repo.ration.IRepoRation

class RepoRationInMemoryDeleteTest: RepoRationDeleteTest() {
    override val repo: IRepoRation = RepoRationInMemory(initObjects)
}