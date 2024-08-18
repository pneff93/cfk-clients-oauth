import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.config.SaslConfigs
import java.util.*

class ProducerProperties {

    fun configureProperties() : Properties{

        val settings = Properties()
        settings.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
        settings.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
        settings.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "broker:9092")

        settings.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT")
        settings.setProperty(SaslConfigs.SASL_MECHANISM, "OAUTHBEARER")
        settings.setProperty(SaslConfigs.SASL_LOGIN_CONNECT_TIMEOUT_MS, "15000")
        settings.setProperty(SaslConfigs.SASL_OAUTHBEARER_TOKEN_ENDPOINT_URL, "https://login.microsoftonline.com/<tenant-id>/oauth2/v2.0/token")
        settings.setProperty(SaslConfigs.SASL_LOGIN_CALLBACK_HANDLER_CLASS, "org.apache.kafka.common.security.oauthbearer.secured.OAuthBearerLoginCallbackHandler")
        settings.setProperty(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.oauthbearer.OAuthBearerLoginModule required clientId='<client-id>' clientSecret='<client-secret' scope='<Azure client id of the broker application>/.default';")

        return settings
    }
}