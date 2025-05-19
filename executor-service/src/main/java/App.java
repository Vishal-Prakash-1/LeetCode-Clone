import config.Constants;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class App {
    public static void main(String[] args) {
        setupDirs();

        MessageProcessor messageProcessor = new MessageProcessor();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getKafkaProps());
        consumer.subscribe(Collections.singletonList(Constants.TOPIC_NAME));

        System.out.println("App is listening for messages...");
        while(true) {
            ConsumerRecords<String, String> messages = consumer.poll(Duration.ofMillis(100));
            messages.forEach(messageProcessor::processMsg);
        }
    }

    private static void setupDirs() {
        if (!Constants.RUN_DIR.exists()) {
            Constants.RUN_DIR.mkdir();
        }
    }

    private static Properties getKafkaProps() {
        Properties properties = new Properties();

        properties.setProperty("bootstrap.servers", Constants.KAFKA_SERVER);
//        properties.setProperty("security.protocol", "SSL");
//        properties.setProperty("ssl.truststore.location", Constants.TRUSTSTORE_LOCATION);
//        properties.setProperty("ssl.truststore.password", Constants.TRUSTSTORE_PASSWORD);
//        properties.setProperty("ssl.keystore.type", "PKCS12");
//        properties.setProperty("ssl.keystore.location", Constants.KEYSTORE_LOCATION);
//        properties.setProperty("ssl.keystore.password", Constants.KEYSTORE_PASSWORD);
//        properties.setProperty("ssl.key.password", Constants.KEY_PASSWORD);
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        properties.setProperty("group.id", Constants.GROUP_ID);

        return properties;
    }
}
