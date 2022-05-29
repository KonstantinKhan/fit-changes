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
    initObjects: List<ProductModel> = emptyList()
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
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMinutes(60)))
                .build()
        )
    }

    init {
        initObjects.forEach {
            save(it)
        }
        cache.forEach {
            println("В базу добавлена запись: ${it.value} ")
        }
    }

    private fun save(item: ProductModel): DbProductResponse {
        val row = ProductRow(item)
        val errors = mutableListOf<CommonErrorModel>()
        if (row.productId == null) {
            errors.add(
                CommonErrorModel(
                    field = "productId",
                    message = "Id cannot be null or empty"
                )
            )
        }
        if (row.authorId.isNullOrEmpty()) {
            errors.add(
                CommonErrorModel(
                    field = "authorId",
                    message = "authorId cannot be null or empty"
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
        if (row.caloriesPerHundredGrams == null || row.caloriesPerHundredGrams.isNaN()) {
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
            errors.forEach { println(it.message) }
            return DbProductResponse(
                result = null,
                isSuccess = false,
                errors = errors
            )
        }
        cache.put(row.productId, row)
        return DbProductResponse(
            result = row.toInternal(),
            isSuccess = true
        )
    }

    override suspend fun create(req: DbProductModelRequest): DbProductResponse {
        return save(
            req.product.copy(
                productId = ProductIdModel(UUID.randomUUID())
            )
        )
    }

    override suspend fun read(req: DbProductIdRequest): DbProductResponse = cache.get(req.id.asString())?.let {
        DbProductResponse(
            it.toInternal(),
            isSuccess = true
        )
    } ?: DbProductResponse(
        isSuccess = false,
        errors = listOf(
            CommonErrorModel(
                field = "id",
                message = "Id not found"
            )
        ),
        result = null
    )

    override suspend fun update(req: DbProductModelRequest): DbProductResponse {
        val key = req.product.productId.takeIf { it != ProductIdModel.NONE }?.asString()
            ?: return DbProductResponse(
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
            save(req.product)
        else {
            DbProductResponse(
                isSuccess = false,
                errors = listOf(
                    CommonErrorModel(
                        field = "id",
                        message = "Not found"
                    )
                ),
                result = null
            )
        }
    }

    override suspend fun delete(req: DbProductIdRequest): DbProductResponse {

        val key = req.id.takeIf { it != ProductIdModel.NONE }?.asString()
            ?: return DbProductResponse(
                isSuccess = false,
                errors = listOf(
                    CommonErrorModel(
                        field = "id",
                        message = "Id must not be null or empty"
                    )
                ),
                result = null
            )
        val row = cache.get(key)
            ?: return DbProductResponse(
                isSuccess = false,
                errors = listOf(CommonErrorModel(field = "id", message = "Id not found")),
                result = null
            )
        cache.remove(key)
        return DbProductResponse(
            isSuccess = true,
            result = row.toInternal()
        )
    }

    override suspend fun search(req: DbProductFilterRequest): DbProductsResponse {
        val foundProducts = mutableListOf<ProductModel>()
        cache.forEach { it ->
            it.takeIf {
                it.value.productName?.lowercase()?.contains(req.query) == true
            }?.let {
                foundProducts.add(it.value.toInternal())
            }
        }

        return DbProductsResponse(
            isSuccess = true,
            result = foundProducts
        )
    }

    override suspend fun allProducts(): DbProductsResponse {
        val products = mutableListOf<ProductModel>()
        cache.forEach { products.add(it.value.toInternal()) }
        return DbProductsResponse(
            isSuccess = true,
            result = products
        )
    }
}
