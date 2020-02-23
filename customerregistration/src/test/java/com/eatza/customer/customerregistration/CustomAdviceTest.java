
package com.eatza.customer.customerregistration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.eatza.customer.customerregistration.controller.CustomerRegController;
import com.eatza.customer.customerregistration.domain.CustomerForm;
import com.eatza.customer.customerregistration.exceptionhandler.CustomerException;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = { "kafka.topic.girishTechie = girishTechie",
		"kafka.bootstrap.servers = localhost:9092", "zookeeper.groupId=girishTechie", "zookeeper.host=localhost:2181" })
public class CustomAdviceTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerRegController controller;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new CustomerException("customer not found: ")).build();
	}

	@Test
	public void test505errorwithcustomexception() throws Exception {
		System.out.println("controller: " + controller);
		when(controller.createCustomer(any(CustomerForm.class)))
				.thenThrow(new CustomerException("customer not found: "));
		mockMvc.perform(post("/customer/register")).andDo(print()).andExpect(status().isNotImplemented());
	}
}
