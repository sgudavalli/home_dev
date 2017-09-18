package connectors;

import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class MapRConnectivity {

	public static void main(String[] args) throws IOException {

		final String TOPIC_AUDIT_EVENTS = "/sample-stream:audit";

		// set up the producer
		KafkaProducer<String, String> producer;

		Properties properties = new Properties();
		properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		properties.put("bootstrap.servers", "192.168.0.10:9092");

		producer = new KafkaProducer<String, String>(properties);

		ProducerRecord<String, String> rec = new ProducerRecord<String, String>(
				TOPIC_AUDIT_EVENTS, "hello123Audit", "mycurrentAuditRecord");

		producer.send(rec);

		producer.close();

	}

}
