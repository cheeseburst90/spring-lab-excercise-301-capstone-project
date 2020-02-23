package com.eatza.customer.customerregistration.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eatza.customer.customerregistration.domain.CustomerForm;
import com.eatza.customer.customerregistration.entity.Customer;
import com.eatza.customer.customerregistration.exceptionhandler.CustomerException;
import com.eatza.customer.customerregistration.repository.CustomerServiceRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerServiceRepository customerServiceRepository;
	
	@Autowired
	private KafkaSender kafkaSender;
	
	private Logger logger = LoggerFactory.getLogger(CustomerService.class);

	public Customer createCustomer(CustomerForm customerForm) throws CustomerException {
		try {
			Customer customer = new Customer();
			customer.setActive(true);
			customer.setFirstName(customerForm.getFirstName());
			customer.setLastName(customerForm.getLastName());
			customer.setDefaultAddress(customerForm.getDefaultAddress());
			customer.setDefaultPaymentMode(customerForm.getDefaultPaymentMode());
			customer.setFoodPreference(customerForm.getFoodPreference());
			customerServiceRepository.save(customer);
			//kafkaMsgSend(customer.getFirstName());
			return customer;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomerException("customer not created: ");
		}

	}

	public void kafkaMsgSend(String firstName) throws IOException {
		kafkaSender.publishMessage("customer created with firstname: "+firstName+" and the customer is active now.");
	}

	public List<Customer> getCustomer() {
		return customerServiceRepository.findAll();
	}
	
	public Customer getCustomer(String firstName) throws CustomerException {
		try {
			Customer customer = customerServiceRepository.findByFirstName(firstName);
			return customer;
		} catch (Exception e) {
			logger.error("error occured: "+e);
			throw new CustomerException("no customer found: ");
		}
	}
	
	public String deleteCustomer(String customerFirstName) throws CustomerException {
		Customer customer = getCustomer(customerFirstName);
		if (customer!=null) {
			customer.setActive(false);
			customerServiceRepository.saveAndFlush(customer);
			return "success";
		}else {
			throw new CustomerException("customer not deleted properly: ");
		}
	}

}
