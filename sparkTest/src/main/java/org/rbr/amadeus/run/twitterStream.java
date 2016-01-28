package org.rbr.amadeus.run;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.twitter.hbc.core.Constants;
import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * Hello world!
 *
 */
public class twitterStream {
	private static final String topic = "twitterStreams";

	private static final String consumerKey = "QO7GYjM35DBIj89fgsdluN6CK";
	private static final String consumerSecret = "joZdcSNGsgNI87v6WC4npSTwrxbNYQ1fxMsFbiF869ZoU6Ga6v";
	private static final String token = "26683719-XU6lyae2JnFwGP1xgw5jrTISIVNRcfaSB784tUvOv";
	private static final String secret = "sW8osKeOTsxWKELFfYpE1c05cbIVP1zVvkhnx6qnZos9v";

	public static void main(String[] args) {
		twitterStream.run();
	}

	public static void run() {

		/* Kafka specific producer config & codebase */
		Properties props = new Properties();
		props.put("metadata.broker.list", "localhost:9092");
		props.put("serializer.class", "kafka.serializer.StringEncoder");

		ProducerConfig kafkaproducer = new ProducerConfig(props);
		Producer<String, String> producer = new Producer<String, String>(
				kafkaproducer);

		/* queue to capture tweets */
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);

		/* list of tags to capture */
		StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
		endpoint.trackTerms(Lists.newArrayList("Trump" ));

		/* twitter Authentication */
		Authentication auth = new OAuth1(consumerKey, consumerSecret, token,
				secret);

		/* twitter client hpc api */
		Client client = new ClientBuilder().hosts(Constants.STREAM_HOST)
				.endpoint(endpoint).authentication(auth)
				.processor(new StringDelimitedProcessor(queue)).build();
		client.connect();

		/* reading 1000 messages */
		for (int msgRead = 0; msgRead < 10; msgRead++) {
			KeyedMessage<String, String> message = null;
			try {
				message = new KeyedMessage<String, String>(topic, queue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			producer.send(message);
		}

		producer.close();
		client.stop();
	}

}
