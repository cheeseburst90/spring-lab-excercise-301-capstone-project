
package com.eatza.customer.customerregistration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.eatza.customer.customerregistration.domain.CustomerForm;
import com.eatza.customer.customerregistration.entity.Customer;
import com.eatza.customer.customerregistration.exceptionhandler.CustomerException;
import com.eatza.customer.customerregistration.repository.CustomerServiceRepository;
import com.eatza.customer.customerregistration.service.CustomerService;
import com.eatza.customer.customerregistration.service.KafkaSender;

@RunWith(SpringRunner.class)

@SpringBootTest
public class CustomerServiceIntegrationTest {

	@Autowired
	private CustomerService customerService;

	@MockBean
	CustomerServiceRepository repository;

	@MockBean
	private KafkaSender kafkaSender;

	@Test
	public void createCustomer() throws IOException, CustomerException {
		CustomerForm customerForm = new CustomerForm();
		customerForm.setFirstName("afdeaf");
		customerForm.setLastName("SDSd");
		System.out.println(RandomStringUtils.randomAlphabetic(1000));
		doNothing().when(kafkaSender).publishMessage(RandomStringUtils.randomAlphabetic(1000));
		when(customerService.createCustomer(customerForm))
				.thenReturn(new Customer(1, "afdeaf", "SDSd", null, null, null, Boolean.TRUE));
		Customer customer = customerService.createCustomer(customerForm);
		assertThat(customer.isActive()).isTrue();
	}
	
	@Test
	public void createCustomerNegativeCase() throws IOException, CustomerException {
		
		Assertions.assertThrows(CustomerException.class, () -> {
			CustomerForm customerForm = new CustomerForm();
			customerForm.setFirstName(null);
			customerForm.setLastName("SDSd");
			customerForm.setFoodPreference("veg");
			when(repository.save(any(Customer.class)))
					.thenThrow(new CustomerException("customer cannot be created"));
			Customer customerSaved = customerService.createCustomer(customerForm);
			
		    });
	}

	@Test
	public void getCustomer() {
		List<Customer> stream = Arrays.asList(
				new Customer(RandomUtils.nextInt(), RandomStringUtils.randomAlphabetic(10),
						RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10),
						RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), Boolean.TRUE),
				new Customer(RandomUtils.nextInt(), RandomStringUtils.randomAlphabetic(10),
						RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10),
						RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), Boolean.TRUE),
				new Customer(RandomUtils.nextInt(), RandomStringUtils.randomAlphabetic(10),
						RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10),
						RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), Boolean.TRUE),
				new Customer(RandomUtils.nextInt(), RandomStringUtils.randomAlphabetic(10),
						RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10),
						RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), Boolean.TRUE));
		when(repository.findAll()).thenReturn(stream);
		assertThat(customerService.getCustomer().size()).isEqualTo(4);
	}

	@Test
	public void getCustomerFirstName() throws CustomerException {
		String firstName = "girish";
		when(repository.findByFirstName(firstName)).thenReturn(new Customer(RandomUtils.nextInt(), firstName,
				RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10),
				RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), Boolean.TRUE));
		assertThat(customerService.getCustomer(firstName).getFirstName()).isEqualTo(firstName);
		assertTrue(customerService.getCustomer(firstName).isActive());
	}

	@Test
	public void deleteCustomer() throws CustomerException {
		String firstName = "girish";
		Customer customer = new Customer(RandomUtils.nextInt(), firstName, RandomStringUtils.randomAlphabetic(10),
				RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10),
				RandomStringUtils.randomAlphabetic(10), Boolean.TRUE);
		when(repository.findByFirstName(firstName)).thenReturn(customer);
		customerService.deleteCustomer(firstName);
		verify(repository).saveAndFlush(customer);
	}
	
	@Test()
	public void deleteCustomerNegativeCase() throws CustomerException {
		
		Assertions.assertThrows(CustomerException.class, () -> {
			String firstName = "girish";
			Customer customer = new Customer(RandomUtils.nextInt(), "bharatwaj", RandomStringUtils.randomAlphabetic(10),
					RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10),
					RandomStringUtils.randomAlphabetic(10), Boolean.TRUE);
			when(repository.findByFirstName(firstName)).thenThrow(CustomerException.class);
			customerService.deleteCustomer(firstName);
		    });
	}
	
}
