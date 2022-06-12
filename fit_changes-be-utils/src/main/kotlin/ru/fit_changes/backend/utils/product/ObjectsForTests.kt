package ru.fit_changes.backend.utils.product

import ru.fit_changes.backend.common.product.models.*
import ru.fit_changes.backend.mapping.product.toTransport
import ru.fit_changes.openapi.models.*
import java.util.*

const val REQUEST_ID_0001 = "requestID:0001"

const val PRODUCT_ID_0001 = "productID:0001"
const val PRODUCT_ID_0002 = "productID:0002"

const val AUTHOR_ID_0001 = "authorID:0001"
const val AUTHOR_ID_0002 = "authorID:0002"

val BEEF_FILLED_CREATABLE_PRODUCT = CreatableProduct(
    productName = "Говядина",
    caloriesPerHundredGrams = 187.0,
    proteinsPerHundredGrams = 18.9,
    fatsPerHundredGrams = 12.4,
    carbohydratesPerHundredGrams = 0.0
)

val BEEF_NOT_CALORIES = BEEF_FILLED_CREATABLE_PRODUCT.copy(
    caloriesPerHundredGrams = null
)

val BEEF_NOT_FILLED_CREATABLE_PRODUCT = CreatableProduct(
    productName = null,
    caloriesPerHundredGrams = null,
    proteinsPerHundredGrams = null,
    fatsPerHundredGrams = null,
    carbohydratesPerHundredGrams = null
)

val BEEF_FILLED_UPDATABLE_PRODUCT = UpdatableProduct(
    productName = "Говядина",
    caloriesPerHundredGrams = 187.0,
    proteinsPerHundredGrams = 18.9,
    fatsPerHundredGrams = 12.4,
    carbohydratesPerHundredGrams = 0.0,
    productId = PRODUCT_ID_0001
)

val BEEF_NOT_FILLED_UPDATABLE_PRODUCT = BEEF_FILLED_UPDATABLE_PRODUCT.copy(
    fatsPerHundredGrams = null
)

val BEEF_FILLED_MODEL = ProductModel(
    productName = "Говядина",
    caloriesPerHundredGrams = CaloriesModel(187.0),
    proteinsPerHundredGrams = ProteinsModel(18.9),
    fatsPerHundredGrams = FatsModel(12.4),
    carbohydratesPerHundredGrams = CarbohydratesModel(0.0),
)

val CHICKEN_FILLED_MODEL = ProductModel(
    productName = "Филе куриное",
    caloriesPerHundredGrams = CaloriesModel(110.0),
    proteinsPerHundredGrams = ProteinsModel(21.0),
    fatsPerHundredGrams = FatsModel(3.0),
    carbohydratesPerHundredGrams = CarbohydratesModel(0.0),
)

val CHICKEN_THIGH_FILLED_MODEL = ProductModel(
    productName = "Бедро куриное",
    caloriesPerHundredGrams = CaloriesModel(185.0),
    proteinsPerHundredGrams = ProteinsModel(21.3),
    fatsPerHundredGrams = FatsModel(11.0),
    carbohydratesPerHundredGrams = CarbohydratesModel(0.0),
)

val BEEF_FILLED_RESPONSE = BEEF_FILLED_MODEL.toTransport()

val MEAL = Meal(
    mealName = Meal.MealName.BREAKFAST,
    products = listOf(
        UsedProduct(
            authorId = UUID.randomUUID().toString(),
            category = null,
            productName = "Филе куриной грудки",
            caloriesPerHundredGrams = 110.0,
            proteinsPerHundredGrams = 21.0,
            fatsPerHundredGrams = 3.0,
            carbohydratesPerHundredGrams = 0.0,
            productId = UUID.randomUUID().toString(),
            weight = 200.0,
            parentProductId = UUID.randomUUID().toString(),
            caloriesFact = 220.0,
            proteinsFact = 42.0,
            fatsFact = 6.0,
            carbohydratesFact = 0.0

        )
    )

)

val RATION = CreatableRation(
    authorId = UUID.randomUUID().toString(),
    dateRation = "12.06.2022",
    caloriesNorm = 2000.0,
    proteinsNorm = 100.0,
    fatsNorm = 50.0,
    carbohydratesNorm = 200.0,
    caloriesFact = 2200.0,
    proteinsFact = 120.0,
    fatsFact = 56.0,
    carbohydratesFact = 240.0,
    meals = listOf(MEAL)
)