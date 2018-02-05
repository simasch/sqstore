package store.customer.control;

import store.customer.entity.Customer;

import javax.annotation.PostConstruct;
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
    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void insertCustomerPeterMuster() {
        TypedQuery<Customer> q = em.createNamedQuery(Customer.FIND_BY_NAME, Customer.class);
        q.setParameter(Customer.NAME, PETER_MUSTER);
        List<Customer> list = q.getResultList();

        if (list.isEmpty()) {
            Customer customer = new Customer();
            customer.setName(PETER_MUSTER);
            em.persist(customer);
            em.flush();
        }
    }
}
