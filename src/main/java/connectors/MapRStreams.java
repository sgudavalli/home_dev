package connectors;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class MapRStreams {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		final String TOPIC_AUDIT_EVENTS = "/sample-stream:audit";

		// set up the producer
		KafkaProducer<String, String> producer;

		Properties properties = new Properties();
		properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		properties.put("bootstrap.servers", "maprdemo:9092");

		producer = new KafkaProducer<String, String>(properties);

		ProducerRecord<String, String> rec = new ProducerRecord<String, String>(
				TOPIC_AUDIT_EVENTS, "hello123Audit", "mycurrentAuditRecord");

		producer.send(rec);

		producer.close();
		
	}

}
