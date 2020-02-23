package com.practice.kafka.messaging.helper;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

	@Value("${zookeeper.groupId}")
	private String zookeeperGroupId;

	@Value("${zookeeper.host}")
	private String zookeeperHost;

	@Value("${kafka.bootstrap.servers}")
	private String kafkaBootstrapServers;

	@Value("${kafka.topic.girishTechie}")
	private String topicName;

	private Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

	private org.apache.kafka.clients.consumer.KafkaConsumer<String, String> kafkaConsumer;
	
	private org.apache.kafka.clients.consumer.KafkaConsumer<String, byte[]> kafkaConsumer_1;

	public byte[] subscribeMessage() {
		byte[] resp = null;
		kafkaConsumer = new org.apache.kafka.clients.consumer.KafkaConsumer<String, String>(getConsumerProps("String"));
		kafkaConsumer_1 = new org.apache.kafka.clients.consumer.KafkaConsumer<String, byte[]>(getConsumerProps("image"));
		try {
			kafkaConsumer.subscribe(Arrays.asList(topicName));
			logger.info("finished:  ceac");

			ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(10000));

			for (ConsumerRecord<String, String> record : records) {
				String message = record.value();
				logger.info("Received message: " + message);
				try {
					JSONObject receivedJsonObject = new JSONObject(message);
					logger.info("Index of deserialized JSON object: " + receivedJsonObject.getInt("index"));
				} catch (JSONException e) {
					logger.error(e.getMessage());
				}
				{
					Map<TopicPartition, OffsetAndMetadata> commitMessage = new HashMap<>();

					commitMessage.put(new TopicPartition(record.topic(), record.partition()),
							new OffsetAndMetadata(record.offset() + 1));
					kafkaConsumer.commitSync(commitMessage);
					logger.info("Offset committed to Kafka.");
				}
			}
			kafkaConsumer_1.subscribe(Arrays.asList(topicName));
			ConsumerRecords<String, byte[]> records_1 = kafkaConsumer_1.poll(Duration.ofMillis(10000));
			for (ConsumerRecord<String, byte[]> record : records_1) {
				logger.info("image recieved: " );
				resp = (byte[]) record.value();
				{
					Map<TopicPartition, OffsetAndMetadata> commitMessage = new HashMap<>();

					commitMessage.put(new TopicPartition(record.topic(), record.partition()),
							new OffsetAndMetadata(record.offset() + 1));
					kafkaConsumer_1.commitSync(commitMessage);
					logger.info("Offset committed to Kafka.");
				}
			}
			logger.info("outside for loop:");
		} finally {
			
		}
		return resp;
	}

	private Properties getConsumerProps(String type) {
		Properties consumerProperties = new Properties();
		consumerProperties.put("bootstrap.servers", kafkaBootstrapServers);
		consumerProperties.put("group.id", zookeeperGroupId);
		consumerProperties.put("zookeeper.session.timeout.ms", "10000");
		consumerProperties.put("zookeeper.sync.time.ms", "10000");
		consumerProperties.put("auto.commit.enable", "false");
		consumerProperties.put("auto.commit.interval.ms", "1000");
		consumerProperties.put("consumer.timeout.ms", "-1");
		consumerProperties.put("max.poll.records", "100");
		consumerProperties.put("max.poll.interval.ms", "10");
		if ("String".equals(type)) {
			consumerProperties.put("value.deserializer",
					"org.apache.kafka.common.serialization.StringDeserializer");
		}else if("image".equals(type)) {
			consumerProperties.put("value.deserializer",
					ByteArrayDeserializer.class);
			
		}
		consumerProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		return consumerProperties;
	}

}
