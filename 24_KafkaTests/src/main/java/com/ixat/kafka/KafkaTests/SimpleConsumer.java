package com.ixat.kafka.KafkaTests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.utils.ZkUtils;

public class SimpleConsumer {
	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("zookeeper.connect", "localhost:2181");
		props.put("group.id", "g1");
		props.put("zookeeper.session.timeout.ms", "500");
		props.put("zookeeper.sync.time.ms", "250");
		props.put("auto.commit.interval.ms", "1000");
		
		//if you want to read from start
		//props.put("auto.offset.reset", "smallest");
		//ZkUtils.maybeDeletePath("localhost:2181", "/consumers/g1");

		String topic = "A1";

		ConsumerConnector consumer = Consumer
				.createJavaConsumerConnector(new ConsumerConfig(props));
		Map<String, Integer> topicCount = new HashMap<String, Integer>();
		topicCount.put(topic, 1);

		Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams = consumer
				.createMessageStreams(topicCount);
		List<KafkaStream<byte[], byte[]>> streams = consumerStreams.get(topic);
		for (final KafkaStream stream : streams) {
			ConsumerIterator<byte[], byte[]> it = stream.iterator();
			while (it.hasNext()) {
				System.out.println("Message from Single Topic: "
						+ new String(it.next().message()));
			}
		}
		if (consumer != null) {
			consumer.shutdown();
		}

	}
}
