package store.test;

import org.junit.After;
import org.junit.Before;
import store.test.context.ApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * The {@link ApplicationContextTest} is used to provide the handling of starting and stopping the {@link EntityManager}
 * <p>
 * It uses H2 in-memory database
 */
public abstract class ApplicationContextTest {

    protected static ApplicationContext applicationContext = new ApplicationContext("store");
    protected static EntityManager em = applicationContext.getEntityManager();
    protected EntityTransaction transaction;

    /**
     * This method is called before each test method.
     * {@see BeforeTest}
     */
    @Before
    public void beforeSuper() {
        transaction = em.getTransaction();
        transaction.begin();
    }

    /**
     * This method is called after each test method.
     * {@see AfterTest}
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
