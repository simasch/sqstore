package store.customer.control;

import store.customer.entity.Customer;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CustomerRepository {

    EntityManager em;

    public CustomerRepository(EntityManager em) {
        this.em = em;
    }

    public List<Customer> findCustomerByName(String name) {
        TypedQuery<Customer> query = em.createQuery("select c from Customer c where c.name = :name", Customer.class);
        query.setParameter("name", name);
        return query.getResultList();

    }
}
