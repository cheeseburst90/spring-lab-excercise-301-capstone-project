package com.eatza.customer.customerregistration.controller;

import com.eatza.customer.customerregistration.domain.CustomerForm;
import com.eatza.customer.customerregistration.entity.Customer;
import com.eatza.customer.customerregistration.exceptionhandler.CustomerException;
import com.eatza.customer.customerregistration.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerRegController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "/customer/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> createCustomer(CustomerForm customerForm) throws CustomerException {
        return new ResponseEntity<Customer>(customerService.createCustomer(customerForm), HttpStatus.OK);
    }

    @GetMapping(value = "/customer/getcustomers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>> getCustomers() {
        return new ResponseEntity<List<Customer>>(customerService.getCustomer(), HttpStatus.OK);
    }

    @GetMapping(value = "/customer/getcustomerByFirstName/{firstName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> getCustomerByFirstName(@PathVariable("firstName") String firstName) throws CustomerException {
        return new ResponseEntity<Customer>(customerService.getCustomer(firstName), HttpStatus.OK);
    }

    @PutMapping("/customer/delete")
    public String deleteCustomer(String customerFirstName) throws CustomerException {
        return customerService.deleteCustomer(customerFirstName);
    }
}
