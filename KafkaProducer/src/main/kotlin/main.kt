import kotlinx.coroutines.runBlocking

fun main() {

    val kafkaProducer = KafkaProducer()
    val properties = ProducerProperties()

//    kafkaProducer.createTopic(properties)

    runBlocking {

        while (true) {

            val threadKafkaProducer = kafkaProducer.produceEvents(properties)
            threadKafkaProducer.join()
        }
    }
}