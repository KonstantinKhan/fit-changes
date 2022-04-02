package ru.fit_changes.backend.repo.inmemory

import org.ehcache.Cache
import org.ehcache.CacheManager
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ExpiryPolicyBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import ru.fit_changes.backend.common.models.CommonErrorModel
import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.backend.common.product.models.ProductModel
import ru.fit_changes.backend.repo.product.*
import java.time.Duration
import java.util.*

class RepoProductInMemory(

) : IRepoProduct {

    private val cache: Cache<String, ProductRow> = let {
        val cacheManager: CacheManager = CacheManagerBuilder
            .newCacheManagerBuilder()
            .build(true)

        cacheManager.createCache(
            "product-cache",
            CacheConfigurationBuilder
                .newCacheConfigurationBuilder(
                    String::class.java,
                    ProductRow::class.java,
                    ResourcePoolsBuilder.heap(100)
                )
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMinutes(1)))
                .build()
        )
    }

    init {
        println("Repo in memory has been init")
    }

    private fun save(item: ProductModel): DbProductResponse {
        println("save start")
        val row = ProductRow(item)
        println("row is: $row")
        val errors = mutableListOf<CommonErrorModel>()
        if (row.productId == null) {
            errors.add(
                CommonErrorModel(
                    field = "productId",
                    message = "Id cannot be null or empty"
                )
            )
        }
        if (row.productName == null) {
            errors.add(
                CommonErrorModel(
                    field = "productName",
                    message = "Product name cannot be null or empty"
                )
            )
        }
        if (row.caloriesPerHundredGrams == null) {
            errors.add(
                CommonErrorModel(
                    field = "caloriesPerHundredGrams",
                    message = "Calories cannot be null or empty"
                )
            )
        }
        if (row.proteinsPerHundredGrams == null) {
            errors.add(
                CommonErrorModel(
                    field = "proteinsPerHundredGrams",
                    message = "Proteins cannot be null or empty"
                )
            )
        }
        if (row.fatsPerHundredGrams == null) {
            errors.add(
                CommonErrorModel(
                    field = "fatsPerHundredGrams",
                    message = "Fats cannot be null or empty"
                )
            )
        }
        if (row.carbohydratesPerHundredGrams == null) {
            errors.add(
                CommonErrorModel(
                    field = "carbohydratesPerHundredGrams",
                    message = "Carbohydrates cannot be null or empty"
                )
            )
        }
        if (errors.size > 0) {
            return DbProductResponse(
                result = null,
                isSuccess = false,
                errors = errors
            )
        }
        println("no errors")

        cache.put(row.productId, row)
        println("put complete")
        cache.forEach { println(it.value) }
        return DbProductResponse(
            result = row.toInternal(),
            isSuccess = true
        )
    }

    override suspend fun create(req: DbProductModelRequest): DbProductResponse {
        println("create")
        return save(
            req.product.copy(
                productId = ProductIdModel(UUID.randomUUID())
            )
        )
    }

    override suspend fun read(req: DbProductIdRequest): DbProductResponse {
        TODO("Not yet implemented")
    }

    override suspend fun update(req: DbProductModelRequest): DbProductResponse {
        TODO("Not yet implemented")
    }

    override suspend fun delete(req: DbProductIdRequest): DbProductResponse {
        TODO("Not yet implemented")
    }

    override suspend fun search(req: DbProductFilterRequest): DbProductsResponse {
        TODO("Not yet implemented")
    }
}
