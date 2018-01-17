package store.customer.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import store.BaseTestWithEntityManager;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class CustomerTest extends BaseTestWithEntityManager {

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
        TypedQuery<Customer> query = em.createQuery("select c from Customer c where c.name = :name", Customer.class);
        query.setParameter("name", PETER_MUSTER);
        List<Customer> list = query.getResultList();

        Assert.assertEquals(1, list.size());
    }
}
