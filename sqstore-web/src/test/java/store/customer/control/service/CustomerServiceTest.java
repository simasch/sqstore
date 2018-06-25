package store.customer.control.service;

import org.junit.BeforeClass;
import org.junit.Test;
import store.common.test.TestContainer;

public class CustomerServiceTest {

    private static TestContainer testContainer = new TestContainer("store");
    private static CustomerService customerService;

    @BeforeClass
    public static void init() {
        customerService = testContainer.getBean(CustomerService.class);
    }

    @Test
    public void tick() {
        customerService.tick();
    }

    @Test
    public void tickAsync() {
    }

    @Test
    public void consumerCreatedAsync() {
    }

    @Test
    public void sendMessage() {
    }

    @Test
    public void findAllCustomers() {
    }

    @Test
    public void findCustomerByName() {
    }

    @Test
    public void saveCustomer() {
    }

    @Test
    public void findCustomerById() {
    }
}