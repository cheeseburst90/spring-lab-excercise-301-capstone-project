package com.eatza.notify.communique.helper;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    @Getter
    private List<String> msg = new ArrayList<String>();

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "girishTechie")
    public void receive(String payload) {
        LOGGER.info("received payload='{}'", payload);
        msg.add(payload);
        latch.countDown();
    }
}
