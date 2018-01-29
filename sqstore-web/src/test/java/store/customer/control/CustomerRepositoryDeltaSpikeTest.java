package store.customer.control;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import store.customer.entity.Customer;

import javax.inject.Inject;

@RunWith(CdiTestRunner.class)
public class CustomerRepositoryDeltaSpikeTest {

    @Inject
    private CustomerDeltaSpikeRepository customerDeltaSpikeRepository;

    @Test
    public void findCustomer() {
        Customer customer = new Customer();
        customer.setName("Peter Muster");
        customerDeltaSpikeRepository.save(customer);

        customerDeltaSpikeRepository.findCustomerByName("Peter Muster");
    }
}
