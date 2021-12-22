package com.eatza.customer.customerregistration.repository;

import com.eatza.customer.customerregistration.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerServiceRepository extends JpaRepository<Customer, Long> {

    Customer findByFirstName(String firstName);

}
