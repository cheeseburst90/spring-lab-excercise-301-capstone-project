package com.eatza.customer.customerregistration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eatza.customer.customerregistration.entity.Customer;

@Repository
public interface CustomerServiceRepository extends JpaRepository<Customer, Long>{
	
	Customer findByFirstName(String firstName);

}
