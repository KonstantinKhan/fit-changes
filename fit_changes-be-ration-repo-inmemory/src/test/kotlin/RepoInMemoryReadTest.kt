import ru.fit_changes.backend.ration.repo.inmemory.RepoRationInMemory
import ru.fit_changes.backend.ration.repo.test.RepoRationReadTest
import ru.fit_changes.backend.repo.ration.IRepoRation

class RepoInMemoryReadTest : RepoRationReadTest() {
    override val repo: IRepoRation = RepoRationInMemory()
}