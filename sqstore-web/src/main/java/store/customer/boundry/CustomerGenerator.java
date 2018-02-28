package store.customer.boundry;

import store.customer.control.CustomerService;
import store.customer.entity.Customer;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Startup
@Singleton
public class CustomerGenerator {

    public static final String PETER_MUSTER = "Peter Muster";

    @EJB
    private CustomerService customerService;

    @PostConstruct
    public void insertCustomerPeterMuster() {
        Customer customer = customerService.findCustomerByName(PETER_MUSTER);

        if (customer == null) {
            customer = new Customer();
            customer.setName(PETER_MUSTER);
            customerService.saveCustomer(customer);
        }
    }
}
