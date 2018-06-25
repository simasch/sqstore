package store.customer.control.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import store.customer.control.repository.CustomerRepository;
import store.customer.entity.Customer;
import store.customer.entity.CustomerInfoDTO;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceUsingMockitoTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

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
        when(customerRepository.findByName("Test")).thenReturn(Optional.of(createTestCustomer()));

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

    private Customer createTestCustomer() {
        Customer testCustomer = new Customer();
        testCustomer.setName("Test");
        return testCustomer;
    }

}
