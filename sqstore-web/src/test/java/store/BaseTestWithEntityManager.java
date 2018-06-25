package store;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import store.common.test.TestContainer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * The {@link BaseTestWithEntityManager} is used to provide the handling of starting and stopping
 * the {@link EntityManager}
 */
public abstract class BaseTestWithEntityManager {

    private static TestContainer testContainer = new TestContainer("store");
    protected static EntityManager em = testContainer.getEntityManager();
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

}
