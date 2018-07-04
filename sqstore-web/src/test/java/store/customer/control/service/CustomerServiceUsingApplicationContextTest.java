package store.customer.control.service;

import org.junit.Test;
import store.customer.entity.Customer;
import store.customer.entity.CustomerInfoDTO;
import store.test.ApplicationContextTest;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class CustomerServiceUsingApplicationContextTest extends ApplicationContextTest {

    private CustomerService customerService = applicationContext.getBean(CustomerService.class);

    @Test
    public void tick() {
        customerService.tick();
    }

    @Test
    public void tickAsync() {
        customerService.tickAsync();
    }

    @Test
    public void consumerCreatedAsync() {
        customerService.consumerCreatedAsync(new Customer());
    }

    @Test
    public void findAllCustomers() {
        List<CustomerInfoDTO> customers = customerService.findAllCustomers();

        assertTrue(customers.isEmpty());
    }

    @Test
    public void findCustomerByName() {
        createTestCustomer();

        Optional<Customer> optionalCustomer = customerService.findCustomerByName("Test");

        assertTrue(optionalCustomer.isPresent());
        assertEquals("Test", optionalCustomer.get().getName());
    }

    @Test
    public void saveCustomer() {
        customerService.saveCustomer(new Customer());
    }

    @Test
    public void findCustomerById() {
        Optional<Customer> optionalCustomer = customerService.findCustomerById(1);

        assertFalse(optionalCustomer.isPresent());
    }

    private void createTestCustomer() {
        Customer testCustomer = new Customer();
        testCustomer.setName("Test");
        em.persist(testCustomer);
        em.flush();
    }


}