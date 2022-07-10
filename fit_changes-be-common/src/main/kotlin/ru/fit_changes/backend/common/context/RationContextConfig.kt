package ru.fit_changes.backend.common.context

import ru.fit_changes.backend.repo.ration.IRepoRation

data class RationContextConfig(
    val repoRationTest: IRepoRation = IRepoRation.NONE,
    val repoRationProd: IRepoRation = IRepoRation.NONE,
)
