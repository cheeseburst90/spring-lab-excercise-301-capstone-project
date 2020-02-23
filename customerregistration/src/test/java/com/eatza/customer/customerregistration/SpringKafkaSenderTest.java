package com.eatza.customer.customerregistration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.springframework.kafka.test.assertj.KafkaConditions.key;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasValue;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.eatza.customer.customerregistration.service.KafkaSender;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@TestPropertySource(properties = { "kafka.topic.girishTechie = girishTechie",
		"kafka.bootstrap.servers = localhost:9092", "zookeeper.groupId=girishTechie", "zookeeper.host=localhost:2181" })
public class SpringKafkaSenderTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringKafkaSenderTest.class);

	private static String SENDER_TOPIC = "girishTechie";

	@Autowired
	private KafkaSender sender;

	private KafkaMessageListenerContainer<String, String> container;

	private BlockingQueue<ConsumerRecord<String, String>> records = new LinkedBlockingQueue<>();

	@ClassRule
	public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, SENDER_TOPIC);

	@Before
	public void setUp() throws Exception {
		Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps("sender", "false",
				embeddedKafka.getEmbeddedKafka());

		DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<String, String>(
				consumerProperties);

		ContainerProperties containerProperties = new ContainerProperties(SENDER_TOPIC);

		container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);

		records = new LinkedBlockingQueue<>();

		container.setupMessageListener(new MessageListener<String, String>() {
			@Override
			public void onMessage(ConsumerRecord<String, String> record) {
				LOGGER.debug("test-listener received message='{}'", record.toString());
				records.add(record);
			}
		});

		container.start();

		ContainerTestUtils.waitForAssignment(container, embeddedKafka.getEmbeddedKafka().getPartitionsPerTopic());
	}

	@After
	public void tearDown() {
		// stop the container
		container.stop();
	}

	@Test
	public void testSend() throws InterruptedException, IOException {
		// send the message
		String greeting = "Hello Spring Kafka Sender!";
		sender.publishMessage(greeting);
		System.out.println(records);
		ConsumerRecord<String, String> received = records.poll(10, TimeUnit.SECONDS);
		//assertThat(received).has(key(null));
	}
}
