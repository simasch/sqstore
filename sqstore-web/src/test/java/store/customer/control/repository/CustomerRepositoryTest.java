package store.customer.control.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import store.common.test.JpaTest;
import store.common.test.TestContainer;
import store.customer.entity.Customer;
import store.customer.entity.CustomerInfoDTO;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerRepositoryTest extends JpaTest {

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
    public void findCustomer() {
        Optional<Customer> customerOptional = customerRepository.findCustomerByName(PETER_MUSTER);

        assertTrue(customerOptional.isPresent());
        assertEquals(PETER_MUSTER, customerOptional.get().getName());
    }

    @Test
    public void findCustomersByName() {
        List<Customer> list = customerRepository.findCustomersByName(PETER_MUSTER);

        Assert.assertFalse(list.isEmpty());
        assertEquals(PETER_MUSTER, list.get(0).getName());
    }

    @Test
    public void findCustomerInfoDTOs() {
        List<CustomerInfoDTO> list = customerRepository.findCustomerInfoDTOs(PETER_MUSTER);

        assertEquals(1, list.size());
        assertEquals(PETER_MUSTER, list.get(0).getName());
    }

    @Test
    public void findAllDTOs() {
        List<CustomerInfoDTO> list = customerRepository.findAllDTOs();

        assertEquals(1, list.size());
        assertEquals(PETER_MUSTER, list.get(0).getName());
    }

    @Test
    public void findById() {
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());

        assertTrue(optionalCustomer.isPresent());
    }
}
