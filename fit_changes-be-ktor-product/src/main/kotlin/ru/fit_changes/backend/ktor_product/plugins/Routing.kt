package ru.fit_changes.backend.ktor_product.plugins

import io.ktor.server.routing.*
import kotlinx.coroutines.*
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import ru.fit_changes.backend.ktor_product.KtorKafkaConsumer
import java.time.Duration
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean


@OptIn(DelicateCoroutinesApi::class)
fun Route.kafka() {
    val isClosed = AtomicBoolean(false)
    val scope = KtorKafkaConsumer()
    val consumerCoroutineContext = newSingleThreadContext("KtorKafkaConsumer")

    scope.launch(context = consumerCoroutineContext) {
        val consumer = kafkaConsumer()
        val producer = kafkaProducer()

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

fun kafkaConsumer(): KafkaConsumer<String, String> {
    val props = Properties().apply {
        put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092")
        put(ConsumerConfig.GROUP_ID_CONFIG, "products")
        put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
        put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
    }
    return KafkaConsumer<String, String>(props)
}

fun kafkaProducer(): KafkaProducer<String, String> {
    val props = Properties().apply {
        put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092")
        put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
        put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
    }
    return KafkaProducer<String, String>(props)
}