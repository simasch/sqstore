package store.customer.control;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import store.BaseTestWithEntityManager;
import store.customer.control.CustomerRepository;
import store.customer.entity.Customer;

import javax.persistence.TypedQuery;
import java.util.List;

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
        List<Customer> list = customerRepository.findCustomerByName(PETER_MUSTER);

        Assert.assertEquals(1, list.size());
    }
}
