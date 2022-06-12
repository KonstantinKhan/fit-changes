package ru.fit_changes.backend.product.logics.auth

import kotlinx.coroutines.runBlocking
import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.common.context.ContextConfig
import ru.fit_changes.backend.common.context.CorStatus
import ru.fit_changes.backend.common.context.Operations
import ru.fit_changes.backend.common.models.AuthorIdModel
import ru.fit_changes.backend.common.models.CaloriesModel
import ru.fit_changes.backend.common.models.ProteinsModel
import ru.fit_changes.backend.common.models.WorkMode
import ru.fit_changes.backend.common.models.enums.BePrincipalRelations
import ru.fit_changes.backend.common.product.models.*
import ru.fit_changes.backend.product.logics.ProductCrud
import ru.fit_changes.backend.product.logics.utils.testUser
import ru.fit_changes.backend.product.logics.utils.testUserBan
import ru.fit_changes.backend.repo.inmemory.RepoProductInMemory
import ru.fit_changes.backend.utils.product.*
import kotlin.test.*

class ProductCrudAuthTest {

    @Test
    fun createProductSuccessTest() {
        val repo = RepoProductInMemory()
        val crud = ProductCrud(
            config = ContextConfig(
                repoProductTest = repo
            )
        )
        val context = BeContext(
            workMode = WorkMode.TEST,
            requestProduct = CHICKEN_FILLED_MODEL,
            operation = Operations.CREATE,
            principal = testUser()
        )
        runBlocking {
            crud.create(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        assertNotEquals(ProductModel(), context.responseProduct)
        assertTrue(context.errors.isEmpty())
        with(context.responseProduct) {
            assertTrue(productId.asString().isNotBlank())
            assertContains(permissions, ProductPermissions.READ)
            assertContains(permissions, ProductPermissions.UPDATE)
            assertContains(permissions, ProductPermissions.DELETE)
        }
    }

    @Test
    fun createProductFailingTestOtherOperation() {
        val repo = RepoProductInMemory()
        val crud = ProductCrud(
            config = ContextConfig(
                repoProductTest = repo
            )
        )
        val context = BeContext(
            workMode = WorkMode.TEST,
            requestProduct = CHICKEN_FILLED_MODEL,
            operation = Operations.READ,
            principal = testUser()
        )
        runBlocking {
            crud.create(context)
        }
        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(ProductModel(), context.responseProduct)
        assertTrue(context.errors.isNotEmpty())
    }

    @Test
    fun createProductFailingTestBan() {
        val repo = RepoProductInMemory()
        val crud = ProductCrud(
            config = ContextConfig(
                repoProductTest = repo
            )
        )
        val context = BeContext(
            workMode = WorkMode.TEST,
            requestProduct = CHICKEN_FILLED_MODEL,
            operation = Operations.CREATE,
            principal = testUserBan()
        )
        runBlocking {
            crud.create(context)
        }
        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(ProductModel(), context.responseProduct)
        assertTrue(context.errors.isNotEmpty())
    }

    @Test
    fun createProductFailingTestNoId() {
        val repo = RepoProductInMemory()
        val crud = ProductCrud(
            config = ContextConfig(
                repoProductTest = repo
            )
        )
        val context = BeContext(
            workMode = WorkMode.TEST,
            requestProduct = CHICKEN_FILLED_MODEL.copy(proteinsPerHundredGrams = ProteinsModel.NONE),
            operation = Operations.CREATE,
            principal = testUser()
        )
        runBlocking {
            crud.create(context)
        }
        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(ProductModel(), context.responseProduct)
        assertTrue(context.errors.isNotEmpty())
    }

    @Test
    fun readProductAuthorSuccessTest() {
        val repo = RepoProductInMemory(
            listOf(
                BEEF_FILLED_MODEL.copy(
                    productId = ProductIdModel(PRODUCT_ID_0001),
                    authorId = AuthorIdModel(AUTHOR_ID_0001)
                )
            )
        )
        val crud = ProductCrud(
            config = ContextConfig(
                repoProductTest = repo
            )
        )
        val context = BeContext(
            workMode = WorkMode.TEST,
            requestProductId = ProductIdModel(PRODUCT_ID_0001),
            operation = Operations.READ,
            principal = testUser()
        )
        runBlocking {
            crud.read(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        with(context.responseProduct) {
            assertEquals(3, permissions.size)
            assertContains(permissions, ProductPermissions.READ)
            assertContains(permissions, ProductPermissions.UPDATE)
            assertContains(permissions, ProductPermissions.DELETE)
        }
    }

    @Test
    fun readProductPublicSuccessTest() {
        val repo = RepoProductInMemory(
            listOf(
                BEEF_FILLED_MODEL.copy(
                    productId = ProductIdModel(PRODUCT_ID_0001),
                    authorId = AuthorIdModel(AUTHOR_ID_0002)
                )
            )
        )
        val crud = ProductCrud(
            config = ContextConfig(
                repoProductTest = repo
            )
        )
        val context = BeContext(
            workMode = WorkMode.TEST,
            requestProductId = ProductIdModel(PRODUCT_ID_0001),
            operation = Operations.READ,
            principal = testUser()
        )
        runBlocking {
            crud.read(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        with(context.responseProduct) {
            assertEquals(1, permissions.size)
            assertContains(permissions, ProductPermissions.READ)
        }
    }

    @Test
    fun readProductPublicFailingTest() {
        val repo = RepoProductInMemory(
            listOf(
                BEEF_FILLED_MODEL.copy(
                    productId = ProductIdModel(PRODUCT_ID_0001),
                    authorId = AuthorIdModel(AUTHOR_ID_0002)
                )
            )
        )
        val crud = ProductCrud(
            config = ContextConfig(
                repoProductTest = repo
            )
        )
        val context = BeContext(
            workMode = WorkMode.TEST,
            requestProductId = ProductIdModel(PRODUCT_ID_0002),
            operation = Operations.READ,
            principal = testUser()
        )
        runBlocking {
            crud.read(context)
        }
        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(ProductModel(), context.responseProduct)
        assertTrue(context.errors.isNotEmpty())
    }

    @Test
    fun updateProductAuthorSuccessTest() {
        val repo = RepoProductInMemory(
            listOf(
                BEEF_FILLED_MODEL.copy(
                    productId = ProductIdModel(PRODUCT_ID_0001),
                    authorId = AuthorIdModel(AUTHOR_ID_0001)
                )
            )
        )
        val crud = ProductCrud(
            config = ContextConfig(
                repoProductTest = repo
            )
        )
        val context = BeContext(
            workMode = WorkMode.TEST,
            requestProduct = BEEF_FILLED_MODEL.copy(
                productId = ProductIdModel(PRODUCT_ID_0001),
                authorId = AuthorIdModel(AUTHOR_ID_0001),
                caloriesPerHundredGrams = CaloriesModel(190.0)
            ),
            operation = Operations.UPDATE,
            principal = testUser()
        )
        runBlocking {
            crud.update(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        assertEquals(
            BEEF_FILLED_MODEL.copy(
                productId = ProductIdModel(PRODUCT_ID_0001),
                authorId = AuthorIdModel(AUTHOR_ID_0001),
                caloriesPerHundredGrams = CaloriesModel(190.0),
                permissions = mutableSetOf(
                    ProductPermissions.READ,
                    ProductPermissions.UPDATE,
                    ProductPermissions.DELETE
                )
            ),
            context.responseProduct
        )
        assertTrue(context.errors.isEmpty())
        with(context.responseProduct) {
            assertContains(permissions, ProductPermissions.READ)
            assertContains(permissions, ProductPermissions.UPDATE)
            assertContains(permissions, ProductPermissions.DELETE)
        }
    }

    @Test
    fun updateProductPublicFailingTest() {
        val repo = RepoProductInMemory(
            listOf(
                BEEF_FILLED_MODEL.copy(
                    productId = ProductIdModel(PRODUCT_ID_0001),
                    authorId = AuthorIdModel(AUTHOR_ID_0002)
                )
            )
        )
        val crud = ProductCrud(
            config = ContextConfig(
                repoProductTest = repo
            )
        )
        val context = BeContext(
            workMode = WorkMode.TEST,
            requestProduct = BEEF_FILLED_MODEL.copy(
                productId = ProductIdModel(PRODUCT_ID_0001),
                authorId = AuthorIdModel(AUTHOR_ID_0001),
                caloriesPerHundredGrams = CaloriesModel(190.0)
            ),
            operation = Operations.UPDATE,
            principal = testUser()
        )
        runBlocking {
            crud.update(context)
        }
        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(ProductModel(), context.responseProduct)
        assertEquals(1, context.errors.size)
    }

    @Test
    fun updateProductFailingTestIncorrectValue() {
        val repo = RepoProductInMemory(
            listOf(
                BEEF_FILLED_MODEL.copy(
                    productId = ProductIdModel(PRODUCT_ID_0001),
                    authorId = AuthorIdModel(AUTHOR_ID_0001)
                )
            )
        )
        val crud = ProductCrud(
            config = ContextConfig(
                repoProductTest = repo
            )
        )
        val context = BeContext(
            workMode = WorkMode.TEST,
            requestProduct = BEEF_FILLED_MODEL.copy(
                productId = ProductIdModel(PRODUCT_ID_0001),
                authorId = AuthorIdModel(AUTHOR_ID_0001),
                caloriesPerHundredGrams = CaloriesModel.NONE
            ),
            operation = Operations.UPDATE,
            principal = testUser()
        )
        runBlocking {
            crud.update(context)
        }
        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(ProductModel(), context.responseProduct)
        assertEquals(1, context.errors.size)
    }

    @Test
    fun deleteProductSuccessAuthor() {
        val repo = RepoProductInMemory(
            listOf(
                BEEF_FILLED_MODEL.copy(
                    productId = ProductIdModel(PRODUCT_ID_0001),
                    authorId = AuthorIdModel(AUTHOR_ID_0001)
                )
            )
        )
        val crud = ProductCrud(
            config = ContextConfig(
                repoProductTest = repo
            )
        )
        val context = BeContext(
            workMode = WorkMode.TEST,
            requestProductId = ProductIdModel(PRODUCT_ID_0001),
            operation = Operations.DELETE,
            principal = testUser()
        )
        runBlocking {
            crud.delete(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        assertEquals(
            BEEF_FILLED_MODEL.copy(
                productId = ProductIdModel(PRODUCT_ID_0001),
                authorId = AuthorIdModel(AUTHOR_ID_0001),
                permissions = mutableSetOf(
                    ProductPermissions.READ,
                    ProductPermissions.UPDATE,
                    ProductPermissions.DELETE
                ),
                principalRelations = setOf(BePrincipalRelations.AUTHOR)
            ),
            context.responseProduct
        )
    }

    @Test
    fun deleteProductFailingOtherUser() {
        val repo = RepoProductInMemory(
            listOf(
                BEEF_FILLED_MODEL.copy(
                    productId = ProductIdModel(PRODUCT_ID_0001),
                    authorId = AuthorIdModel(AUTHOR_ID_0001)
                )
            )
        )
        val crud = ProductCrud(
            config = ContextConfig(
                repoProductTest = repo
            )
        )
        val context = BeContext(
            workMode = WorkMode.TEST,
            requestProductId = ProductIdModel(PRODUCT_ID_0001),
            operation = Operations.DELETE,
            principal = testUser().copy(authorId = AuthorIdModel(AUTHOR_ID_0002))
        )
        runBlocking {
            crud.delete(context)
        }
        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(ProductModel(), context.responseProduct)
    }

    @Test
    fun deleteProductFailingNotId() {
        val repo = RepoProductInMemory(
            listOf(
                BEEF_FILLED_MODEL.copy(
                    productId = ProductIdModel(PRODUCT_ID_0001),
                    authorId = AuthorIdModel(AUTHOR_ID_0001)
                )
            )
        )
        val crud = ProductCrud(
            config = ContextConfig(
                repoProductTest = repo
            )
        )
        val context = BeContext(
            workMode = WorkMode.TEST,
            requestProductId = ProductIdModel(PRODUCT_ID_0002),
            operation = Operations.DELETE,
            principal = testUser()
        )
        runBlocking {
            crud.delete(context)
        }
        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(ProductModel(), context.responseProduct)
    }

    @Test
    fun searchProductSuccess() {
        val repo = RepoProductInMemory(
            listOf(
                BEEF_FILLED_MODEL.copy(
                    productId = ProductIdModel(PRODUCT_ID_0001),
                    authorId = AuthorIdModel(AUTHOR_ID_0001)
                ),
                CHICKEN_FILLED_MODEL.copy(
                    productId = ProductIdModel(PRODUCT_ID_0002),
                    authorId = AuthorIdModel(AUTHOR_ID_0002)
                )
            )
        )
        val crud = ProductCrud(
            config = ContextConfig(
                repoProductTest = repo
            )
        )
        val context = BeContext(
            workMode = WorkMode.TEST,
            requestProductFilter = ProductSearchFilter(
                searchStr = "вя"
            ),
            operation = Operations.SEARCH,
            principal = testUser()
        )
        runBlocking {
            crud.search(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)

    }
}