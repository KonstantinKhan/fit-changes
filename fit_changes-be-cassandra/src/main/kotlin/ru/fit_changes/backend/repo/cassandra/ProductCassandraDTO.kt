package ru.fit_changes.backend.repo.cassandra

import com.datastax.oss.driver.api.core.type.DataType
import com.datastax.oss.driver.api.core.type.DataTypes
import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import ru.fit_changes.backend.common.product.models.ProductIdModel
import ru.fit_changes.backend.common.product.models.ProductModel

@Entity
data class ProductCassandraDTO(
    @PartitionKey
    @CqlName(COLUMN_PRODUCT_ID)
    val productId: String?,
    @CqlName(COLUMN_PRODUCT_NAME)
    val productName: String?,
    @CqlName(COLUMN_CALORIES_PER_HUNDRED_GRAMS)
    val caloriesPerHundredGrams: Double?,
    @CqlName(COLUMN_PROTEINS_PER_HUNDRED_GRAMS)
    val proteinsPerHundredGrams: Double?,
    @CqlName(COLUMN_FATS_PER_HUNDRED_GRAMS)
    val fatsPerHundredGrams: Double?,
    @CqlName(COLUMN_CARBOHYDRATES_PER_HUNDRED_GRAMS)
    val carbohydratePerHundredGrams: Double?
) {
    constructor(model: ProductModel) : this(
        productId = model.productId.takeIf { it != ProductIdModel.NONE }?.asString(),
        productName = model.productName.takeIf { it.isNotBlank() },
        caloriesPerHundredGrams = model.caloriesPerHundredGrams,
        proteinsPerHundredGrams = model.proteinsPerHundredGrams,
        fatsPerHundredGrams = model.fatsPerHundredGrams,
        carbohydratePerHundredGrams = model.carbohydratePerHundredGrams
    )

    companion object {
        const val TABLE_NAME = "product"
        const val COLUMN_PRODUCT_ID = "product_id"
        const val COLUMN_PRODUCT_NAME = "product_name"
        const val COLUMN_CALORIES_PER_HUNDRED_GRAMS = "calories_per_hundred_grams"
        const val COLUMN_PROTEINS_PER_HUNDRED_GRAMS = "proteins_per_hundred_grams"
        const val COLUMN_FATS_PER_HUNDRED_GRAMS = "fats_per_hundred_grams"
        const val COLUMN_CARBOHYDRATES_PER_HUNDRED_GRAMS = "carbohydrates_per_hundred_grams"
    }

    fun toProductModel(): ProductModel = ProductModel(
        productName = productName ?: "",
        caloriesPerHundredGrams = caloriesPerHundredGrams ?: 0.0,
        proteinsPerHundredGrams = proteinsPerHundredGrams ?: 0.0,
        fatsPerHundredGrams = fatsPerHundredGrams ?: 0.0,
        carbohydratePerHundredGrams = carbohydratePerHundredGrams ?: 0.0,
        productId = productId?.let { ProductIdModel(it) } ?: ProductIdModel.NONE,
    )

    fun table(
        keyspace: String,
        tableName: String
    ) = SchemaBuilder
        .createTable(keyspace, tableName)
        .ifNotExists()
        .withPartitionKey(COLUMN_PRODUCT_ID, DataTypes.TEXT)
        .withColumn(COLUMN_PRODUCT_ID, DataTypes.TEXT)
        .withColumn(COLUMN_CALORIES_PER_HUNDRED_GRAMS, DataTypes.DECIMAL)
        .withColumn(COLUMN_PROTEINS_PER_HUNDRED_GRAMS, DataTypes.DECIMAL)
        .withColumn(COLUMN_FATS_PER_HUNDRED_GRAMS, DataTypes.DECIMAL)
        .withColumn(COLUMN_CARBOHYDRATES_PER_HUNDRED_GRAMS, DataTypes.DECIMAL)
        .build()

    fun productNameIndex(keyspace: String, tableName: String, locale: String = "en") =
        SchemaBuilder
            .createIndex()
            .ifNotExists()
            .usingSASI()
            .onTable(keyspace, tableName)
            .andColumn(COLUMN_PRODUCT_NAME)
            .withSASIOptions(
                mapOf(
                    "mode" to "CONTAINS",
                    "tokenization_locale" to locale,
                    "analyzer_class" to "org.apache.cassandra.index.sasi.analyzer.NonTokenizingAnalyzer",
                    "case_sensitive" to "false"
                )
            )
            .build()
}