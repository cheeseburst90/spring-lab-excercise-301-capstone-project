package com.eatza.customer.customerregistration;

import com.eatza.customer.customerregistration.entity.Customer;
import com.eatza.customer.customerregistration.repository.CustomerServiceRepository;
import com.eatza.customer.customerregistration.service.KafkaSender;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryIntegrationTest {

    @Autowired
    private CustomerServiceRepository repository;

    @MockBean
    private KafkaSender kafkaSender;

    @Before
    public void svaeThis() {
        // doNothing().when(kafkaSender.publishMessage(RandomStringUtils.
        // randomAlphabetic(10)));
    }

    @Test
    public void testSaveRepo() {
        Customer customer = new Customer();
        customer.setActive(true);
        customer.setDefaultAddress("mettukuppam");
        customer.setDefaultPaymentMode("usd");
        customer.setFirstName("nachiyappan");
        customer.setLastName("sundaram");
        customer.setFoodPreference("chettinad");
        repository.save(customer);
        assertNotNull(customer.getCustomerId());
    }

    @Test
    public void testSearchRepo() {
        Customer customer = repository.findByFirstName("girish");
        assertNotNull(customer.getCustomerId());
        assertEquals("bharatwaj", customer.getLastName());
        assertNotNull(customer.isActive());
    }

    @Test
    public void testSearchAllRepo() {
        List<Customer> customerList = repository.findAll();
        assertEquals(3, customerList.size());
    }

}
