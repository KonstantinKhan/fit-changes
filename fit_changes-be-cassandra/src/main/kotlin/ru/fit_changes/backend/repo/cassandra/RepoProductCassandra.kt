package ru.fit_changes.backend.repo.cassandra

import kotlinx.coroutines.future.await
import ru.fit_changes.backend.common.models.*
import ru.fit_changes.backend.common.product.models.*
import ru.fit_changes.backend.repo.product.*
import java.util.*

class RepoProductCassandra(
    private val dao: ProductCassandraDAO
) : IRepoProduct {
    override suspend fun create(req: DbProductModelRequest): DbProductResponse {
        val product = req.product.copy(productId = ProductIdModel(UUID.randomUUID()))
        val errors = mutableListOf<CommonErrorModel>()
        with(product) {
            if (productName.isBlank()) {
                errors.add(
                    CommonErrorModel(
                        field = "productName", message = "Product name cannot be null or empty"
                    )
                )
            }
            if (caloriesPerHundredGrams == CaloriesModel.NONE) {
                errors.add(
                    CommonErrorModel(
                        field = "caloriesPerHundredGrams", message = "Calories cannot be null or empty"
                    )
                )
            }
            if (proteinsPerHundredGrams == ProteinsModel.NONE) {
                errors.add(
                    CommonErrorModel(
                        field = "proteinsPerHundredGrams", message = "Proteins cannot be null or empty"
                    )
                )
            }
            if (fatsPerHundredGrams == FatsModel.NONE) {
                errors.add(
                    CommonErrorModel(
                        field = "fatsPerHundredGrams", message = "Fats cannot be null or empty"
                    )
                )
            }
            if (carbohydratesPerHundredGrams == CarbohydratesModel.NONE) {
                errors.add(
                    CommonErrorModel(
                        field = "carbohydratesPerHundredGrams", message = "Carbohydrates cannot be null or empty"
                    )
                )
            }
        }
        if (errors.size > 0) {
            return DbProductResponse(
                result = null, isSuccess = false, errors = errors
            )
        }
        dao.create(ProductCassandraDTO(product)).await()
        val created = dao.read(product.productId.asString()).await()
        return if (created == null) {
            DbProductResponse(
                CommonErrorModel(
                    field = "id", message = "ID not found"
                )
            )
        } else DbProductResponse(created.toProductModel())

    }

    override suspend fun read(req: DbProductIdRequest): DbProductResponse {

        val id = req.id.takeIf { it != ProductIdModel.NONE }?.asString() ?: return DbProductResponse(
            CommonErrorModel(
                field = "id", message = "Id cannot be empty"
            )
        )
        val result = dao.read(id).await()
        return if (result == null) {
            DbProductResponse(
                CommonErrorModel(
                    field = "id", message = "Id not found"
                )
            )
        } else {
            DbProductResponse(result.toProductModel())
        }
    }

    override suspend fun update(req: DbProductModelRequest): DbProductResponse {
        return if (req.product.productId == ProductIdModel.NONE) {
            DbProductResponse(CommonErrorModel(field = "id", message = "Invalid value"))
        } else try {
            dao.update(ProductCassandraDTO(req.product)).await()
            val updated = dao.read(req.product.productId.asString()).await()
            if (updated == null) {
                DbProductResponse(CommonErrorModel(field = "id", message = "Not found"))
            } else {
                DbProductResponse(updated.toProductModel())
            }
        } catch (e: Exception) {
            DbProductResponse(e)
        }
    }

    override suspend fun delete(req: DbProductIdRequest): DbProductResponse {
        return if (req.id == ProductIdModel.NONE) {
            DbProductResponse(CommonErrorModel(field = "id", message = "Invalid value"))
        } else try {
            val deleted = dao.read(req.id.asString()).await()?.also {
                dao.delete(it)
            }
            if (deleted == null) {
                DbProductResponse(CommonErrorModel(field = "id", message = "Id not found"))
            } else {
                DbProductResponse(deleted.toProductModel())
            }
        } catch (e: Exception) {
            DbProductResponse(e)
        }
    }

    override suspend fun search(req: DbProductFilterRequest): DbProductsResponse {
        return try {
            val found = dao.search(req).await().map { it.toProductModel() }
            DbProductsResponse(true, emptyList(), found)
        } catch (e: Exception) {
            println("exception: ${e.message}")
            DbProductsResponse(isSuccess = false, errors = listOf(CommonErrorModel()), emptyList())
        }


    }

    override suspend fun allProducts(): DbProductsResponse {
        TODO("Not yet implemented")
    }
}