package ru.fit_changes.backend.app.ktor.product.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import ru.fit_chages.backend.product.service.ProductService
import ru.fit_changes.backend.app.ktor.product.helpers.handleRoute
import ru.fit_changes.openapi.models.CreateProductRequest
import ru.fit_changes.openapi.models.CreateProductResponse
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

//private fun kafkaProducer(hosts: List<String>): KafkaProducer<String, String> {
//    val props = Properties().apply {
//        put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, hosts)
//        put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
//        put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
//    }
//    return KafkaProducer<String, String>(props)
//}