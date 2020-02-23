package com.practice.kafka.messaging.helper;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {

	@Value("${kafka.topic.girishTechie}")
	private String topicName;

	@Value("${kafka.bootstrap.servers}")
	private String kafkaBootstrapServers;

	private Logger logger = LoggerFactory.getLogger(KafkaSender.class);

	public void publishMessage() throws IOException {
		KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerProps("String"));
		sendTestMessagesToKafka(producer);
		KafkaProducer<String, byte[]> producer_images = new KafkaProducer<>(getProducerProps("image"));
		sendTestMessagesToKafkaImage(producer_images);
	}

	private void sendTestMessagesToKafkaImage(KafkaProducer<String, byte[]> producer_images) throws IOException {
		BufferedImage originalImage = ImageIO.read(new File("/home/girish/Desktop/pic_test.jpeg"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(originalImage, "jpeg", baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		logger.info("Sending Kafka image: "+imageInByte);
		producer_images.send(new ProducerRecord<>(topicName, imageInByte));
	}

	private void sendTestMessagesToKafka(KafkaProducer<String, String> producer) {
		for (int index = 0; index < 10; index++) {
			sendKafkaMessage("The index is now: " + index, producer, topicName);
		}

		for (int index = 0; index < 10; index++) {
			JSONObject jsonObject = new JSONObject();
			JSONObject nestedJsonObject = new JSONObject();
			try {
				jsonObject.put("index", index);
				jsonObject.put("message", "The index is now: " + index);
				nestedJsonObject.put("nestedObjectMessage", "This is a nested JSON object with index: " + index);
				jsonObject.put("nestedJsonObject", nestedJsonObject);
			} catch (JSONException e) {
				logger.error(e.getMessage());
			}
			sendKafkaMessage(jsonObject.toString(), producer, topicName);
		}
	}

	private void sendKafkaMessage(String payload, KafkaProducer<String, String> producer, String topic) {
		logger.info("Sending Kafka message: " + payload);
		producer.send(new ProducerRecord<>(topic, payload));
	}

	private Properties getProducerProps(String type) {
		Properties producerProperties = new Properties();
		producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
		producerProperties.put("acks", "all");
		producerProperties.put(ProducerConfig.RETRIES_CONFIG, 0);
		producerProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		producerProperties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		producerProperties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		if ("String".equals(type)) {
			producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		} else if ("image".equals(type)) {
			producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
		}
		return producerProperties;
	}

}
