package ru.fit_changes.backend.ktor_product.plugins

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.*
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.producer.ProducerRecord
import ru.fitChanges.openapi.models.CreateProductRequest
import ru.fit_chages.backend.product.service.ProductService
import ru.fit_changes.backend.common.context.BeContext
import ru.fit_changes.backend.ktor_product.ConsumerScope
import ru.fit_changes.backend.ktor_product.KafkaConfig
import ru.fit_changes.backend.product.logics.ProductCrud
import java.time.Duration
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean


@OptIn(DelicateCoroutinesApi::class)
fun kafka(
    config: KafkaConfig = KafkaConfig(),
    service: ProductService = ProductService(ProductCrud())
) {
    val isClosed = AtomicBoolean(false)
    val kafkaScope = ConsumerScope()
    val kafkaCoroutineContext = newSingleThreadContext("KtorKafkaConsumer")
    val om = ObjectMapper()

    kafkaScope.launch(context = kafkaCoroutineContext) {
        val consumer = config.kafkaConsumer
        val producer = config.kafkaProducer

        try {
            consumer.subscribe(listOf("product-in"))
        } catch (t: Throwable) {
            println(t.message)
        }

        while (!isClosed.get()) {
            val records: ConsumerRecords<String, String> = consumer.poll(Duration.ofSeconds(1))
            if (!records.isEmpty) {
                records.forEach { record: ConsumerRecord<String, String> ->
                    val context = BeContext()
                    val request = withContext(Dispatchers.IO) {
                        om.readValue(record.value(), CreateProductRequest::class.java)
                    }
                    val response = service.createProduct(context, request)
                    val json = withContext(Dispatchers.IO) {
                        om.writeValueAsString(response)
                    }
                    producer.send(
                        ProducerRecord(
                            "product-out",
                            UUID.randomUUID().toString(),
                            json
                        )
                    )
                }
            }
        }
    }
}