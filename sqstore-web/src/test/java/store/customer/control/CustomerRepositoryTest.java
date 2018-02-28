package store.customer.control;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import store.BaseTestWithEntityManager;
import store.customer.control.CustomerRepository;
import store.customer.entity.Customer;
import store.customer.entity.CustomerInfoDTO;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class CustomerRepositoryTest extends BaseTestWithEntityManager {

    public static final String PETER_MUSTER = "Peter Muster";

    @Before
    public void insertCustomer() {
        Customer customer = new Customer();
        customer.setName(PETER_MUSTER);
        em.persist(customer);
        em.flush();
    }

    @Test
    public void findCustomer() {
        CustomerRepository customerRepository = new CustomerRepository(em);
        Optional<Customer> customerOptional = customerRepository.findCustomerByName(PETER_MUSTER);

        Assert.assertTrue(customerOptional.isPresent());
        Assert.assertEquals(PETER_MUSTER, customerOptional.get().getName());
    }

    @Test
    public void findCustomerDTO() {
        CustomerRepository customerRepository = new CustomerRepository(em);
        List<CustomerInfoDTO> list = customerRepository.findCustomerInfoDTOs(PETER_MUSTER);

        Assert.assertEquals(1, list.size());
        Assert.assertEquals(PETER_MUSTER, list.get(0).getName());
    }

    @Test
    public void findAll() {
        CustomerRepository customerRepository = new CustomerRepository(em);
        List<Customer> list = customerRepository.findAll();

        Assert.assertEquals(1, list.size());
        Assert.assertEquals(PETER_MUSTER, list.get(0).getName());
    }

    @Test
    public void findAllDTOs() {
        CustomerRepository customerRepository = new CustomerRepository(em);
        List<CustomerInfoDTO> list = customerRepository.findAllDTOs();

        Assert.assertEquals(1, list.size());
        Assert.assertEquals(PETER_MUSTER, list.get(0).getName());
    }
}
