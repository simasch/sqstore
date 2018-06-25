package store.common.test;

import org.junit.After;
import org.junit.Before;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * The {@link ApplicationContextTest} is used to provide the handling of starting and stopping
 * the {@link EntityManager}
 */
public abstract class ApplicationContextTest {

    protected static ApplicationContext applicationContext = new ApplicationContext("test");
    protected static EntityManager em = applicationContext.getEntityManager();
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
        em.clear();
    }

}
