package ru.fit_changes.backend.repo.cassandra

import com.datastax.oss.driver.api.core.type.DataTypes
import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import ru.fit_changes.backend.common.models.*
import ru.fit_changes.backend.common.product.models.*

@Entity
data class ProductCassandraDTO(
    @PartitionKey
    @CqlName(COLUMN_PRODUCT_ID)
    val productId: String?,
    @CqlName(COLUMN_PRODUCT_NAME)
    val productName: String?,
    @CqlName(COLUMN_AUTHOR_ID)
    val authorId: String?,
    @CqlName(COLUMN_CALORIES_PER_HUNDRED_GRAMS)
    val caloriesPerHundredGrams: Double?,
    @CqlName(COLUMN_PROTEINS_PER_HUNDRED_GRAMS)
    val proteinsPerHundredGrams: Double?,
    @CqlName(COLUMN_FATS_PER_HUNDRED_GRAMS)
    val fatsPerHundredGrams: Double?,
    @CqlName(COLUMN_CARBOHYDRATES_PER_HUNDRED_GRAMS)
    val carbohydratesPerHundredGrams: Double?
) {
    constructor(model: ProductModel) : this(
        productId = model.productId.takeIf { it != ProductIdModel.NONE }?.asString(),
        authorId = model.authorId.takeIf { it != AuthorIdModel.NONE }?.asString(),
        productName = model.productName.takeIf { it.isNotBlank() },
        caloriesPerHundredGrams = model.caloriesPerHundredGrams.takeIf { it != CaloriesModel.NONE }?.value,
        proteinsPerHundredGrams = model.proteinsPerHundredGrams.takeIf { it != ProteinsModel.NONE }?.value,
        fatsPerHundredGrams = model.fatsPerHundredGrams.takeIf { it != FatsModel.NONE }?.value,
        carbohydratesPerHundredGrams = model.carbohydratesPerHundredGrams.takeIf { it != CarbohydratesModel.NONE }?.value,
    )

    companion object {
        const val TABLE_NAME = "products"
        const val COLUMN_PRODUCT_ID = "product_id"
        const val COLUMN_PRODUCT_NAME = "product_name"
        const val COLUMN_AUTHOR_ID = "author_id"
        const val COLUMN_CALORIES_PER_HUNDRED_GRAMS = "calories_per_hundred_grams"
        const val COLUMN_PROTEINS_PER_HUNDRED_GRAMS = "proteins_per_hundred_grams"
        const val COLUMN_FATS_PER_HUNDRED_GRAMS = "fats_per_hundred_grams"
        const val COLUMN_CARBOHYDRATES_PER_HUNDRED_GRAMS = "carbohydrates_per_hundred_grams"

        fun table(
            keyspace: String,
            tableName: String
        ) = SchemaBuilder
            .createTable(keyspace, tableName)
            .ifNotExists()
            .withPartitionKey(COLUMN_PRODUCT_ID, DataTypes.TEXT)
            .withColumn(COLUMN_PRODUCT_NAME, DataTypes.TEXT)
            .withColumn(COLUMN_AUTHOR_ID, DataTypes.TEXT)
            .withColumn(COLUMN_CALORIES_PER_HUNDRED_GRAMS, DataTypes.DOUBLE)
            .withColumn(COLUMN_PROTEINS_PER_HUNDRED_GRAMS, DataTypes.DOUBLE)
            .withColumn(COLUMN_FATS_PER_HUNDRED_GRAMS, DataTypes.DOUBLE)
            .withColumn(COLUMN_CARBOHYDRATES_PER_HUNDRED_GRAMS, DataTypes.DOUBLE)
            .build()

        fun productNameIndex(keyspace: String, tableName: String, locale: String = "en") =
            SchemaBuilder
                .createIndex()
                .ifNotExists()
                .usingSASI()
                .usingSASI()
                .onTable(keyspace, tableName)
                .andColumn(COLUMN_PRODUCT_NAME)
                .withSASIOptions(
                    mapOf(
                        "mode" to "CONTAINS",
//                        "tokenization_locale" to locale,
//                        "analyzer_class" to "org.apache.cassandra.index.sasi.analyzer.NonTokenizingAnalyzer",
                        "case_sensitive" to "false"
                    )
                )
                .build()

    }

    fun toProductModel(): ProductModel = ProductModel(
        productName = productName ?: "",
        caloriesPerHundredGrams = caloriesPerHundredGrams?.let { CaloriesModel(it) } ?: CaloriesModel.NONE,
        proteinsPerHundredGrams = proteinsPerHundredGrams?.let { ProteinsModel(it) } ?: ProteinsModel.NONE,
        fatsPerHundredGrams = fatsPerHundredGrams?.let { FatsModel(it) } ?: FatsModel.NONE,
        carbohydratesPerHundredGrams = carbohydratesPerHundredGrams?.let { CarbohydratesModel(it) }
            ?: CarbohydratesModel.NONE,
        productId = productId?.let { ProductIdModel(it) } ?: ProductIdModel.NONE,
        authorId = authorId?.let { AuthorIdModel(it) } ?: AuthorIdModel.NONE
    )
}