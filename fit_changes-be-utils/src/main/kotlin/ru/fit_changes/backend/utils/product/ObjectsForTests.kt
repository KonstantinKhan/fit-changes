package ru.fit_changes.backend.utils.product

import ru.fit_changes.backend.common.mappers.toTransport
import ru.fit_changes.backend.common.models.*
import ru.fit_changes.backend.common.models.enums.BeMealName
import ru.fit_changes.backend.common.models.ration.BeMeal
import ru.fit_changes.backend.common.models.ration.BeUsedProduct
import ru.fit_changes.backend.common.models.ration.RationModel
import ru.fit_changes.backend.common.product.models.*
import ru.fit_changes.openapi.models.*
import java.time.Instant
import java.util.*

const val REQUEST_ID_0001 = "requestID:0001"

const val PRODUCT_ID_0001 = "productID:0001"
const val PRODUCT_ID_0002 = "productID:0002"

const val AUTHOR_ID_0001 = "authorID:0001"
const val AUTHOR_ID_0002 = "authorID:0002"

const val DATE_RATION = "2022-06-12T15:00:00.0Z"
const val RATION_ID = "rationID:0001"

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

val CHICKEN_USED_PRODUCT = UsedProduct(
    authorId = UUID.randomUUID().toString(),
    category = null,
    productName = "Филе куриной грудки",
    caloriesPerHundredGrams = 110.0,
    proteinsPerHundredGrams = 21.0,
    fatsPerHundredGrams = 3.0,
    carbohydratesPerHundredGrams = 0.0,
    weight = 200.0,
    parentProductId = UUID.randomUUID().toString(),
    caloriesFact = 220.0,
    proteinsFact = 42.0,
    fatsFact = 6.0,
    carbohydratesFact = 0.0
)

val MEAL = Meal(
    mealName = Meal.MealName.BREAKFAST,
    products = listOf(
        CHICKEN_USED_PRODUCT
    )
)

val CREATABLE_RATION_FILLED = CreatableRation(
    authorId = AUTHOR_ID_0001,
    dateRation = DATE_RATION,
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

val CREATABLE_RATION_WITHOUT_CARBOHYDRATES = CreatableRation(
    authorId = AUTHOR_ID_0001,
    dateRation = DATE_RATION,
    caloriesNorm = 2000.0,
    proteinsNorm = 100.0,
    fatsNorm = 50.0,
    carbohydratesNorm = 200.0,
    caloriesFact = 2200.0,
    proteinsFact = 120.0,
    fatsFact = 56.0,
    carbohydratesFact = 240.0,
    meals = listOf(
        MEAL.copy(
            products = listOf(
                CHICKEN_USED_PRODUCT.copy(
                    carbohydratesFact = null
                )
            )
        )
    )
)

val UPDATABLE_RATION_FILLED = UpdatableRation(
    authorId = AUTHOR_ID_0001,
    dateRation = DATE_RATION,
    caloriesNorm = 2000.0,
    proteinsNorm = 100.0,
    fatsNorm = 50.0,
    carbohydratesNorm = 200.0,
    caloriesFact = 2200.0,
    proteinsFact = 120.0,
    fatsFact = 56.0,
    carbohydratesFact = 240.0,
    meals = listOf(MEAL),
    rationId = RATION_ID
)

val USED_CHICKEN_PRODUCT = BeUsedProduct(
    authorId = AuthorIdModel(AUTHOR_ID_0001),
    category = "",
    productName = "Филе куриной грудки",
    caloriesPerHundredGrams = CaloriesModel(calories = 110.0),
    proteinsPerHundredGrams = ProteinsModel(proteins = 21.0),
    fatsPerHundredGrams = FatsModel(fats = 3.0),
    carbohydratesPerHundredGrams = CarbohydratesModel(carbohydrates = 0.0),
    productId = ProductIdModel(PRODUCT_ID_0001),
    weight = 200.0,
    caloriesFact = CaloriesModel(calories = 220.0),
    proteinsFact = ProteinsModel(proteins = 42.0),
    fatsFact = FatsModel(fats = 6.0),
    carbohydratesFact = CarbohydratesModel(carbohydrates = 0.0),
    parentProductId = ProductIdModel(id = PRODUCT_ID_0002)
)

val RATION_FILLED_MODEL = RationModel(
    authorId = AuthorIdModel(AUTHOR_ID_0001),
    dateRation = Instant.parse(DATE_RATION),
    caloriesNorm = CaloriesModel(calories = 2000.0),
    proteinsNorm = ProteinsModel(proteins = 100.0),
    fatsNorm = FatsModel(fats = 50.0),
    carbohydratesNorm = CarbohydratesModel(carbohydrates = 200.0),
    caloriesFact = CaloriesModel(calories = 2200.0),
    proteinsFact = ProteinsModel(proteins = 120.0),
    fatsFact = FatsModel(fats = 56.0),
    carbohydratesFact = CarbohydratesModel(carbohydrates = 240.0),
    meals = mutableListOf(
        BeMeal(
            mealName = BeMealName.BREAKFAST,
            usedProducts = mutableListOf(
                USED_CHICKEN_PRODUCT
            )
        )
    )
)