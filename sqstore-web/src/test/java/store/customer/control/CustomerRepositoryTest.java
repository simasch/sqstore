package store.customer.control;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import store.customer.entity.Customer;
import store.customer.entity.CustomerInfoDTO;

import javax.inject.Inject;
import java.util.List;

@RunWith(CdiTestRunner.class)
public class CustomerRepositoryTest {

    @Inject
    private CustomerRepository customerRepository;

    @Test
    public void findByName() {
        List<Customer> list = customerRepository.findByName("Peter Muster");

        Assert.assertEquals(0, list.size());
    }

    @Test
    public void findCustomerInfoDTOByName() {
        List<CustomerInfoDTO> list = customerRepository.findCustomerInfoDTOByName("Peter Muster");

        Assert.assertEquals(0, list.size());
    }
}
