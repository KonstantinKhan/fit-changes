package ru.fit_changes.backend.ration.repo.inmemory

import org.ehcache.Cache
import org.ehcache.CacheManager
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ExpiryPolicyBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import ru.fit_changes.backend.common.models.CommonErrorModel
import ru.fit_changes.backend.common.models.ration.RationIdModel
import ru.fit_changes.backend.common.models.ration.RationModel
import ru.fit_changes.backend.repo.ration.*
import java.time.Duration
import java.util.*

class RepoRationInMemory(
    initObjects: List<RationModel> = emptyList()
) : IRepoRation {

    private val cache: Cache<String, RationRow> = let {
        val cacheManager: CacheManager = CacheManagerBuilder
            .newCacheManagerBuilder()
            .build(true)

        cacheManager.createCache(
            "ration-cache",
            CacheConfigurationBuilder
                .newCacheConfigurationBuilder(
                    String::class.java,
                    RationRow::class.java,
                    ResourcePoolsBuilder.heap(100)
                )
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMinutes(60)))
                .build()
        )
    }

    init {
        initObjects.forEach { save(it) }
        cache.forEach {
            println("Был добавлен рацион: ${it.value.dateRation} ${it.value.rationId}")
        }
    }

    private fun save(item: RationModel): DbRationResponse {
        val row = RationRow(item)
        cache.put(row.rationId, row)
        return DbRationResponse(
            isSuccess = true,
            result = row.toInternal()
        )
    }

    override suspend fun create(req: DbRationModelRequest): DbRationResponse {
        return save(
            req.ration.copy(
                rationId = RationIdModel(UUID.randomUUID())
            )
        )
    }

    override suspend fun read(req: DbRationIdRequest): DbRationResponse = cache.get(req.id.asString())?.let {
        DbRationResponse(
            isSuccess = true,
            result = it.toInternal()
        )
    } ?: DbRationResponse(
        isSuccess = false,
        errors = listOf(
            CommonErrorModel(
                field = "id",
                message = "Id not found"
            )
        ),
        result = null
    )

    override suspend fun update(req: DbRationModelRequest): DbRationResponse {
        val key = req.ration.rationId.takeIf { it != RationIdModel.NONE }?.asString() ?: return DbRationResponse(
            isSuccess = false,
            errors = listOf(
                CommonErrorModel(
                    field = "id",
                    message = "Id must not be null or blank"
                )
            ),
            result = null
        )
        return if (cache.containsKey(key))
            save(req.ration)
        else {
            DbRationResponse(
                isSuccess = false,
                errors = listOf(
                    CommonErrorModel(
                        field = "id",
                        message = "Id not found"
                    )
                ),
                result = null
            )
        }
    }

    override suspend fun delete(req: DbRationIdRequest): DbRationResponse {
        val key = req.id.takeIf { it != RationIdModel.NONE }?.asString() ?: return DbRationResponse(
            isSuccess = false,
            errors = listOf(
                CommonErrorModel(
                    field = "id",
                    message = "Id must not be null or empty"
                )
            ),
            result = null
        )
        val row = cache.get(key) ?: return DbRationResponse(
            isSuccess = false,
            errors = listOf(
                CommonErrorModel(
                    field = "id",
                    message = "Id not found"
                )
            ),
            result = null
        )
        cache.remove(key)
        return DbRationResponse(
            isSuccess = true,
            result = row.toInternal()
        )
    }

    override suspend fun search(req: DbRationFilterRequest): DbRationsResponse {
        TODO("Not yet implemented")
    }
}