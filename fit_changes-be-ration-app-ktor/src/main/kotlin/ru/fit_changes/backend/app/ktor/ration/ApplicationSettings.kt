package ru.fit_changes.backend.app.ktor.ration

import ru.fit_changes.backend.common.models.AuthorIdModel
import ru.fit_changes.backend.common.models.ration.RationIdModel
import ru.fit_changes.backend.common.models.ration.RationModel
import ru.fit_changes.backend.ration.repo.inmemory.RepoRationInMemory
import ru.fit_changes.backend.repo.ration.IRepoRation
import java.util.*

inline fun testRepo(block: RepoRationInMemoryBuilder.() -> Unit): IRepoRation =
    RepoRationInMemoryBuilder().apply(block).build()

class RationBuilder {

    var prototype: RationModel? = null
    var rationId: RationIdModel = RationIdModel(UUID.randomUUID())
    var authorId: AuthorIdModel = AuthorIdModel(UUID.randomUUID())

    fun build(): RationModel = prototype ?: RationModel(rationId = rationId, authorId = authorId)
}

class RepoRationInMemoryBuilder {
    private var initObjects: MutableList<RationModel> = mutableListOf()

    fun rations(block: InitObjectsBlock.() -> Unit) {
        val container = InitObjectsBlock().apply(block)
        initObjects.addAll(container.content)
    }

    fun build(): RepoRationInMemory = RepoRationInMemory(initObjects)
}

class InitObjectsBlock {
    private val _content: MutableList<RationModel> = mutableListOf()
    val content: List<RationModel>
        get() = _content


    fun ration(block: RationBuilder.() -> Unit) = RationBuilder().apply(block).build().also {
        _content.add(it)
    }
}