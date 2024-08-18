import org.apache.kafka.clients.admin.KafkaAdminClient
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.errors.SerializationException
import org.apache.logging.log4j.kotlin.logger

class KafkaProducer {

    private val logger = logger(javaClass.name)
    private val topic = "test-topic"

    fun createTopic(properties: ProducerProperties) {


        val client = KafkaAdminClient.create(properties.configureProperties())
        client.createTopics(listOf(NewTopic(topic, 3, 1)))
    }

    fun produceEvents(properties: ProducerProperties): Thread {

        val thread = Thread {

            val kafkaProducer = KafkaProducer<String, String>(properties.configureProperties())

            Thread.sleep(10000)
            logger.info("Kafka Producer started")

            for (i in 1..1000) {

                val key = "$i-key"
                val value = "$i-value"
                try {
                    kafkaProducer.send(
                        ProducerRecord(topic, key, value),
                    ) { m: RecordMetadata, e: Exception? ->
                        when (e) {
                            null -> logger.info("event produced to ${m.topic()}")
                            else -> logger.error("oh no, error occurred")
                        }
                    }
                } catch (e: SerializationException) {
                    logger.error("${e.cause}")
                }

                Thread.sleep(2000)
            }
        }

        thread.start()
        return thread
    }
}