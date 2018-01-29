package store.customer.control;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import store.customer.entity.Customer;

import javax.inject.Inject;
import java.util.List;

@RunWith(CdiTestRunner.class)
public class CustomerRepositoryTest {

    @Inject
    private CustomerRepository customerDeltaSpikeRepository;

    @Test
    public void findCustomer() {
        List<Customer> list = customerDeltaSpikeRepository.findCustomerByName("Peter Muster");

        Assert.assertEquals(0, list.size());
    }
}
