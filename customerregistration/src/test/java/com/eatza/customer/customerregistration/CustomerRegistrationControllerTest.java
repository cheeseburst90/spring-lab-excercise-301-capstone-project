package com.eatza.customer.customerregistration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.eatza.customer.customerregistration.controller.CustomerRegController;
import com.eatza.customer.customerregistration.domain.CustomerForm;
import com.eatza.customer.customerregistration.entity.Customer;
import com.eatza.customer.customerregistration.exceptionhandler.CustomerException;
import com.eatza.customer.customerregistration.repository.CustomerServiceRepository;

@TestPropertySource(properties = { "kafka.topic.girishTechie = girishTechie",
		"kafka.bootstrap.servers = localhost:9092", "zookeeper.groupId=girishTechie", "zookeeper.host=localhost:2181" })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerRegistrationControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private CustomerRegController controller;

	@Mock
	private CustomerServiceRepository repository;

	private Logger logger = LoggerFactory.getLogger(CustomerRegistrationControllerTest.class);
	
	@Test
	public void createCustomer() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		ResponseEntity<Customer> responseEntity = controller
				.createCustomer(new CustomerForm(RandomStringUtils.randomAlphabetic(10),
						RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10),
						RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10)));

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void getCustomers() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		ResponseEntity<List<Customer>> responseEntity = controller.getCustomers();

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void getCustomersByfirstName() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		ResponseEntity<Customer> responseEntity = controller.getCustomerByFirstName("firstName");

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void deleteCustomer() {
		
		Assertions.assertThrows(CustomerException.class, () -> {
			MockHttpServletRequest request = new MockHttpServletRequest();
			RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
			String responseEntity = controller.deleteCustomer("myName");
			assertThat(responseEntity).isEqualTo("customer not deleted properly: ");
		});
	}
}
