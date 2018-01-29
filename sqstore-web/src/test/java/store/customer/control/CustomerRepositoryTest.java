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

public class CustomerRepositoryTest extends BaseTestWithEntityManager {

    public static final String PETER_MUSTER = "Peter Muster";

    @Before
    public void insertCustomer() {
        CustomerGenerator customerGenerator = new CustomerGenerator(em);
        customerGenerator.insertCustomerPeterMuster();
    }

    @Test
    public void findCustomer() {
        CustomerRepository customerRepository = new CustomerRepository(em);
        List<Customer> list = customerRepository.findCustomerByName(PETER_MUSTER);

        Assert.assertEquals(1, list.size());
    }

    @Test
    public void findCustomerDTO() {
        CustomerRepository customerRepository = new CustomerRepository(em);
        List<CustomerInfoDTO> list = customerRepository.findCustomerInfoDTOs(PETER_MUSTER);

        Assert.assertEquals(1, list.size());
    }
}
