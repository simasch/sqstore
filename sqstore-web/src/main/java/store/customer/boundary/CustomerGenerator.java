package store.customer.boundary;

import store.customer.control.service.CustomerService;
import store.customer.entity.Customer;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.Optional;

/**
 * To have some test data in the database inserts a customer if it doesn't exists.
 * <p>
 * It's a {@link Singleton} and exists only once per application server and as a {@link Startup} EJB
 * it's started when the application is deployed and started.
 * <p>
 * <strong>HINT!</strong>
 * You should use Singletons with caution because they could lead to locks and waits.
 * The default {@link javax.ejb.LockType} is WRITE and therefor all the methods calls are serialized.
 */
@Startup
@Singleton
public class CustomerGenerator {

    public static final String PETER_MUSTER = "Peter Muster";

    /**
     * It's good style to delegate all work to a business service.
     * You shouldn't do any business logic in the Startup bean.
     */
    @Inject
    private CustomerService customerService;

    /**
     * After the application is started and the class is instantiated this method will be executed because
     * of the {@link PostConstruct} annotation.
     * We could have multiple methods with this annotation but then the order in which they are called is undefined.
     */
    @PostConstruct
    public void insertCustomerPeterMuster() {
        Optional<Customer> optionalCustomer = customerService.findCustomerByName(PETER_MUSTER);

        if (!optionalCustomer.isPresent()) {
            Customer customer = new Customer();
            customer.setName(PETER_MUSTER);
            customerService.saveCustomer(customer);
        }
    }
}
