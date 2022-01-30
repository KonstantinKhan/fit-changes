package ru.fit_changes.backend.ktor_product.plugins

import kotlinx.coroutines.*
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.producer.ProducerRecord
import ru.fit_changes.backend.ktor_product.ConsumerScope
import ru.fit_changes.backend.ktor_product.KafkaConfig
import java.time.Duration
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean


@OptIn(DelicateCoroutinesApi::class)
fun kafka(config: KafkaConfig = KafkaConfig()) {
    val isClosed = AtomicBoolean(false)
    val kafkaScope = ConsumerScope()
    val kafkaCoroutineContext = newSingleThreadContext("KtorKafkaConsumer")

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
                records.forEach { _ ->
                    producer.send(
                        ProducerRecord(
                            "product-out",
                            UUID.randomUUID().toString(),
                        )
                    )
                }
            }
        }
    }
}