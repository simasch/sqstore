package store.customer.control;

import store.customer.entity.Customer;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Startup
@Singleton
public class CustomerGenerator {

    @PersistenceContext
    private EntityManager em;

    public CustomerGenerator() {
    }

    public CustomerGenerator(EntityManager em) {
        this.em = em;
    }

    @PostConstruct
    public void insertCustomerPeterMuster() {
        Customer customer = new Customer();
        customer.setName("Peter Muster");
        em.persist(customer);
        em.flush();
    }
}
