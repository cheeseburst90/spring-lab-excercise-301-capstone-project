package com.eatza.customer.customerregistration.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;


@Service
public class KafkaSender {

    @Value("${kafka.topic.girishTechie}")
    private String topicName;

    @Value("${kafka.bootstrap.servers}")
    private String kafkaBootstrapServers;

    private Logger logger = LoggerFactory.getLogger(KafkaSender.class);

    public void publishMessage(String message) throws IOException {
        KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerProps());
        sendKafkaMessage(message, producer, topicName);
    }

    private void sendKafkaMessage(String payload, KafkaProducer<String, String> producer, String topic) {
        logger.info("Sending Kafka message: " + payload);
        producer.send(new ProducerRecord<>(topic, payload));
    }

    private Properties getProducerProps() {
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
        producerProperties.put("acks", "all");
        producerProperties.put(ProducerConfig.RETRIES_CONFIG, 0);
        producerProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        producerProperties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        producerProperties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return producerProperties;
    }

}
