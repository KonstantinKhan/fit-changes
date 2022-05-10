package ru.fit_changes.backend.app.ktor.product.routes

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import ru.fit_changes.backend.app.ktor.product.controllers.*
import ru.fit_changes.backend.product.service.ProductService
import ru.fit_changes.openapi.models.BaseMessage
import ru.fit_changes.openapi.models.CreateProductRequest
import ru.fit_changes.openapi.models.CreateProductResponse
import java.time.Duration
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

fun Route.productRouting() =
    route("product") {
        post("create") {

            val om = ObjectMapper()
            val isClosed = AtomicBoolean(false)

            val request = call.receive<BaseMessage>() as CreateProductRequest
            var response: CreateProductResponse

            val propsProducer = Properties().apply {
                put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092")
                put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
                put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
            }

            val propsConsumer = Properties().apply {
                put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092")
                put(ConsumerConfig.GROUP_ID_CONFIG, "products")
                put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
                put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
                put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
            }

            try {
                val producer = KafkaProducer<String, String>(propsProducer)
                val consumer = KafkaConsumer<String, String>(propsConsumer)
                val json = withContext(Dispatchers.IO) {
                    om.writeValueAsString(request)
                }

                try {
                    consumer.subscribe(listOf("product-out"))
                } catch (t: Throwable) {
                    println(t.message)
                }

                withContext(Dispatchers.IO) {
                    producer.send(
                        ProducerRecord(
                            "product-in",
                            UUID.randomUUID().toString(),
                            json
                        )
                    )
                    producer.close()
                }

                while (!isClosed.get()) {
                    val records: ConsumerRecords<String, String> = consumer.poll(Duration.ofSeconds(1))
                    if (!records.isEmpty) {
                        response = withContext(Dispatchers.IO) {
                            om.readValue(records.first().value(), CreateProductResponse::class.java)
                        }
                        call.respond(response)
                        consumer.close()
                        isClosed.set(true)
                    }
                    delay(1000)
                }

            } catch (e: Exception) {
                println(e.message)
            }

//            call.createProduct(productService)
        }
        post("read") {

        }
        post("update") { }
        post("delete") { }
        post("search") { }
    }

fun Application.registerProductRoutes() {
    routing {
        productRouting()
    }
}

fun Route.productRoutingHttp(productService: ProductService) =
    authenticate("auth-jwt") {
        route("product") {
            post("create") {
                call.createProduct(productService)
            }
            post("read") {
                call.readProduct(productService)
            }
            post("update") {
                call.updateProduct(productService)
            }
            post("delete") {
                call.deleteProduct(productService)
            }
            post("search") {
                call.searchProduct(productService)
            }
            get("all_products") {
                call.getAllProducts(productService)
            }
        }
    }


fun Application.registerProductRoutesHttp(productService: ProductService) {
    routing {
        productRoutingHttp(productService)
    }
}
