package ru.fit_changes.backend.app.ktor.product.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import ru.fit_changes.backend.product.service.ProductService
import ru.fit_changes.backend.app.ktor.product.helpers.handleRoute
import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.openapi.models.*
import java.util.*

suspend fun ApplicationCall.createProduct(productService: ProductService) {
    handleRoute<CreateProductRequest, CreateProductResponse>() { request ->
//        val producer = kafkaProducer(listOf("kafka:9092"))
//        val om = ObjectMapper()
//        val json = withContext(Dispatchers.IO) {
//            om.writeValueAsString(request)
//        }
//        producer.send(
//            ProducerRecord(
//                "product-in",
//                UUID.randomUUID().toString(),
//                json
//            )
//        )

//        val createProductRequest = CreateProductRequest(
//            messageType = null,
//            requestId = null,
//            createProduct = null,
//            debug = null
//        )

        productService.createProduct(this, request)
    }
}

suspend fun ApplicationCall.readProduct(productService: ProductService) {
    handleRoute<ReadProductRequest, ReadProductResponse>() { request ->
        productService.readProduct(this, request)
    }
}

suspend fun ApplicationCall.updateProduct(productService: ProductService) {
    handleRoute<UpdateProductRequest, UpdateProductResponse>() { request ->
        productService.updateProduct(this, request)
    }
}

suspend fun ApplicationCall.deleteProduct(productService: ProductService) {
    handleRoute<DeleteProductRequest, DeleteProductResponse>() { request ->
        productService.deleteProduct(this, request)
    }
}

suspend fun ApplicationCall.searchProduct(productService: ProductService) {
    handleRoute<SearchProductRequest, SearchProductResponse>() { request ->
        productService.searchProduct(this, request)
    }
}

suspend fun ApplicationCall.getAllProducts(productService: ProductService) {
    val response = productService.getAllProducts(BeContext())
    respond(response)
}

//private fun kafkaProducer(hosts: List<String>): KafkaProducer<String, String> {
//    val props = Properties().apply {
//        put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, hosts)
//        put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
//        put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
//    }
//    return KafkaProducer<String, String>(props)
//}