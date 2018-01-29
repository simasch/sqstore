package store;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestEntityManagerProducer {

    @Produces
    public EntityManager produceEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("store");
        return emf.createEntityManager();
    }
}
