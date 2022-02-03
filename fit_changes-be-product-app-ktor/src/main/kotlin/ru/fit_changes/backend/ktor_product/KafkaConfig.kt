package ru.fit_changes.backend.ktor_product

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

data class KafkaConfig(
    val kafkaHosts: List<String> = KAFKA_HOSTS,
    val kafkaTopicsIn: List<String> = KAFKA_TOPICS_IN,
    val kafkaGroupId: String = KAFKA_GROUP_ID,
    val kafkaConsumer: Consumer<String, String> = kafkaConsumer(kafkaHosts, kafkaGroupId),
    val kafkaProducer: Producer<String, String> = kafkaProducer(kafkaHosts)
) {
    companion object {
        private const val KAFKA_HOSTS_VAR = "KAFKA_HOSTS"
        private const val KAFKA_TOPICS_IN_VAR = "KAFKA_TOPICS_IN"
        private const val KAFKA_GROUP_ID_VAR = "KAFKA_GROUP_ID"

        val KAFKA_HOSTS: List<String> by lazy { (System.getenv(KAFKA_HOSTS_VAR)).split(Regex("\\s*[,;]\\s*")) }
        val KAFKA_TOPICS_IN: List<String> by lazy { System.getenv(KAFKA_TOPICS_IN_VAR).split(Regex("\\s*[,;]\\s*]")) }
        val KAFKA_GROUP_ID: String by lazy { (System.getenv(KAFKA_GROUP_ID_VAR)) }

        private fun kafkaConsumer(hosts: List<String>, groupId: String): KafkaConsumer<String, String> {
            val props = Properties().apply {
                put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, hosts)
                put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
                put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
                put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
            }
            return KafkaConsumer<String, String>(props)
        }

        private fun kafkaProducer(hosts: List<String>): KafkaProducer<String, String> {
            val props = Properties().apply {
                put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, hosts)
                put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
                put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
            }
            return KafkaProducer<String, String>(props)
        }
    }
}