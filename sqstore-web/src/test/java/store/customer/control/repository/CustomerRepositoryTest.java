package store.customer.control.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import store.BaseTestWithEntityManager;
import store.customer.entity.Customer;
import store.customer.entity.CustomerInfoDTO;

import java.util.List;
import java.util.Optional;

import static store.order.entity.Order_.customer;

public class CustomerRepositoryTest extends BaseTestWithEntityManager {

    public static final String PETER_MUSTER = "Peter Muster";

    private static CustomerRepository customerRepository = new CustomerRepository(em);
    private Customer customer;

    @Before
    public void insertCustomer() {
        customer = new Customer();
        customer.setName(PETER_MUSTER);
        em.persist(customer);
        em.flush();
    }

    @Test
    public void findCustomer() {
        Optional<Customer> customerOptional = customerRepository.findCustomerByName(PETER_MUSTER);

        Assert.assertTrue(customerOptional.isPresent());
        Assert.assertEquals(PETER_MUSTER, customerOptional.get().getName());
    }

    @Test
    public void findCustomersByName() {
        List<Customer> list = customerRepository.findCustomersByName(PETER_MUSTER);

        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(PETER_MUSTER, list.get(0).getName());
    }

    @Test
    public void findCustomerInfoDTOs() {
        List<CustomerInfoDTO> list = customerRepository.findCustomerInfoDTOs(PETER_MUSTER);

        Assert.assertEquals(1, list.size());
        Assert.assertEquals(PETER_MUSTER, list.get(0).getName());
    }

    @Test
    public void findAllDTOs() {
        List<CustomerInfoDTO> list = customerRepository.findAllDTOs();

        Assert.assertEquals(1, list.size());
        Assert.assertEquals(PETER_MUSTER, list.get(0).getName());
    }

    @Test
    public void findById() {
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());

        Assert.assertTrue(optionalCustomer.isPresent());
    }
}
