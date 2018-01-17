package store;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public abstract class BaseTestWithEntityManager {

    protected static EntityManagerFactory emf;
    protected static EntityManager em;
    protected EntityTransaction transaction;

    @Before
    public void beforeSuper() {
        transaction = em.getTransaction();
        transaction.begin();
    }

    @After
    public void afterSuper() {
        em.flush();
        if (transaction.isActive()) {
            transaction.rollback();
        }
    }

    @BeforeClass
    public static void beforeClass() {
        emf = Persistence.createEntityManagerFactory("store");
        em = emf.createEntityManager();
    }

    @AfterClass
    public static void afterClass() {
        if (em.isOpen()) {
            em.close();
        }
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
