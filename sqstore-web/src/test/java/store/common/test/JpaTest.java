package store.common.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * The {@link JpaTest} is used to provide the handling of starting and stopping
 * the {@link EntityManager}
 */
public abstract class JpaTest {

    protected static TestContainer testContainer;
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
     * This method is called beofre all test methods.
     * {@see BeforeClass}
     */
    @BeforeClass
    public static void beforeClassSuper() {
        testContainer = new TestContainer("store");
        em = testContainer.getEntityManager();
    }

    /**
     * This method is called after all test methods were executed.
     * {@see AfterClass}
     */
    @AfterClass
    public static void afterClassSuper() throws Exception {
        testContainer.close();
    }
}
