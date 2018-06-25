package store;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * The {@link BaseTestWithEntityManager} is used to provide the handling of starting and stopping
 * the {@link EntityManager}
 */
public abstract class BaseTestWithEntityManager {

    protected static EntityManagerFactory emf;
    protected static EntityManager em;
    protected EntityTransaction transaction;

    /**
     * This method is called before each test method.
     * {@see Before}
     */
    @Before
    public void beforeSuper() {
        transaction = em.getTransaction();
        transaction.begin();
    }

    /**
     * This method is called after each test method.
     * {@see After}
     */
    @After
    public void afterSuper() {
        em.flush();
        if (transaction.isActive()) {
            transaction.rollback();
        }
    }

    /**
     * This method is called only before the first test method execution.
     * {@see BeforeClass}
     */
    @BeforeClass
    public static void beforeClass() {
        emf = Persistence.createEntityManagerFactory("store");
        em = emf.createEntityManager();
    }

    /**
     * This method is called after all test methods were executed.
     * {@see BeforeClass}
     */
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
