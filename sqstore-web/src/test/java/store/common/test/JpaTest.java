package store.common.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * The {@link JpaTest} is used to provide the handling of starting and stopping
 * the {@link EntityManager}
 */
public abstract class JpaTest {

    protected static EntityManager em = TestContainer.getInstance().getEntityManager();
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
