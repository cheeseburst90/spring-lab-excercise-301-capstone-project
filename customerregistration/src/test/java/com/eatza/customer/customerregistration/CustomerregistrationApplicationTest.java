package com.eatza.customer.customerregistration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = { "kafka.topic.girishTechie = girishTechie",
		"kafka.bootstrap.servers = localhost:9092", "zookeeper.groupId=girishTechie", "zookeeper.host=localhost:2181" })
public class CustomerregistrationApplicationTest {

	@Test
	public void contextLoads() {
	}

}
