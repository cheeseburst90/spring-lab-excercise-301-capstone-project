package com.practice.kafka.messaging.service;

import com.practice.kafka.messaging.helper.KafkaConsumer;
import com.practice.kafka.messaging.helper.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessagingService {

    @Autowired
    KafkaSender kafkaSender;

    @Autowired
    KafkaConsumer kafkaConsumer;

    public void publish() {
        try {
            kafkaSender.publishMessage();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public byte[] subscribeMessage() {
        return kafkaConsumer.subscribeMessage();
    }

}
