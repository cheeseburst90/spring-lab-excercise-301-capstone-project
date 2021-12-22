package com.eatza.notify.communique;

import com.eatza.notify.communique.helper.Receiver;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {SpringKafkaApplicationTest.HELLOWORLD_TOPIC})
public class SpringKafkaApplicationTest {

    static final String HELLOWORLD_TOPIC = "girishTechie";

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringKafkaApplicationTest.class);
    private static String RECEIVER_TOPIC = "girishTechie";
    @ClassRule
    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, RECEIVER_TOPIC);
    @Autowired
    private Receiver receiver;
    @Autowired
    private Sender sender;
    private KafkaTemplate<String, String> template;

    @Before
    private void sendMessgae() {
        sender.send("this is a test message");
    }

    @Test
    public void testReceive() throws Exception {
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertThat(receiver.getLatch().getCount()).isEqualTo(1);
    }

    @Test
    public void testReceivee() throws Exception {
        // send the message
        String greeting = "Hello Spring Kafka Receiver!";
        sender.send(greeting);
        LOGGER.debug("test-sender sent message='{}'", greeting);

        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        // check that the message was received
        assertThat(receiver.getLatch().getCount()).isEqualTo(1);
    }
}
