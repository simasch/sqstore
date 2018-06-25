package store.customer.control.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import store.common.test.ContainerTest;
import store.common.test.TestContainer;
import store.customer.entity.Customer;
import store.customer.entity.CustomerInfoDTO;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerRepositoryTest extends ContainerTest {

    public static final String PETER_MUSTER = "Peter Muster";

    private CustomerRepository customerRepository = TestContainer.getInstance().getBean(CustomerRepository.class);

    private Customer customer;

    @Before
    public void generateTestData() {
        customer = new Customer();
        customer.setName(PETER_MUSTER);
        em.persist(customer);
        em.flush();
    }

    @Test
    public void findByName() {
        Optional<Customer> customerOptional = customerRepository.findByName(PETER_MUSTER);

        assertTrue(customerOptional.isPresent());
        assertEquals(PETER_MUSTER, customerOptional.get().getName());
    }

    @Test
    public void findAllByName() {
        List<Customer> list = customerRepository.findAllByName(PETER_MUSTER);

        Assert.assertFalse(list.isEmpty());
        assertEquals(PETER_MUSTER, list.get(0).getName());
    }

    @Test
    public void findAllCustomerInfoDTOByName() {
        List<CustomerInfoDTO> list = customerRepository.findAllCustomerInfoDTOByName(PETER_MUSTER);

        assertEquals(1, list.size());
        assertEquals(PETER_MUSTER, list.get(0).getName());
    }

    @Test
    public void findAllCustomerInfoDTO() {
        List<CustomerInfoDTO> list = customerRepository.findAllCustomerInfoDTO();

        assertEquals(1, list.size());
        assertEquals(PETER_MUSTER, list.get(0).getName());
    }

    @Test
    public void findById() {
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());

        assertTrue(optionalCustomer.isPresent());
    }
}
