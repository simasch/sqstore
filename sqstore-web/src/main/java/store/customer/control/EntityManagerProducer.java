package store.customer.control;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EntityManagerProducer {

    @PersistenceContext
    private EntityManager em;

    @Produces
    public EntityManager produceEntityManager() {
        return em;
    }
}
