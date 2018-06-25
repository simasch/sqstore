package store.common.test;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * The TestContainer provides basic dependency injection for EJBs and CDI Beans.
 * <p>
 * Everything is a singleton.
 */
public class TestContainer implements AutoCloseable, Serializable {

    private static final long serialVersionUID = 1L;

    private static TestContainer instance;

    private static final String JAVAX_PERSISTENCE_JDBC_URL = "javax.persistence.jdbc.url";
    private static final String JAVAX_PERSISTENCE_JDBC_USER = "javax.persistence.jdbc.user";
    private static final String JAVAX_PERSISTENCE_JDBC_PASSWORD = "javax.persistence.jdbc.password";
    private static final String HIBERNATE_CONNECTION_DRIVER_CLASS = "hibernate.connection.driver_class";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";

    private final EntityManagerFactory emf;
    private final EntityManager em;
    private final DummySessionContext sessionContext;
    private final Map<Class<?>, Object> statelessBeans;

    public static TestContainer getInstance() {
        if (instance == null) {
            instance = new TestContainer();
        }
        return instance;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private TestContainer() {
        this.statelessBeans = new HashMap();
        sessionContext = new DummySessionContext();

        emf = Persistence.createEntityManagerFactory("test", getPropertyMap());
        em = emf.createEntityManager();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected Map getPropertyMap() {
        Map<String, String> hibernateMap = new HashMap();
        if (System.getenv().containsKey(JAVAX_PERSISTENCE_JDBC_URL)) {
            hibernateMap.put(JAVAX_PERSISTENCE_JDBC_URL, System.getenv().get(JAVAX_PERSISTENCE_JDBC_URL));
        }
        if (System.getenv().containsKey(JAVAX_PERSISTENCE_JDBC_USER)) {
            hibernateMap.put(JAVAX_PERSISTENCE_JDBC_USER, System.getenv().get(JAVAX_PERSISTENCE_JDBC_USER));
        }
        if (System.getenv().containsKey(JAVAX_PERSISTENCE_JDBC_PASSWORD)) {
            hibernateMap.put(JAVAX_PERSISTENCE_JDBC_PASSWORD, System.getenv().get(JAVAX_PERSISTENCE_JDBC_PASSWORD));
        }
        if (System.getenv().containsKey(HIBERNATE_CONNECTION_DRIVER_CLASS)) {
            hibernateMap.put(HIBERNATE_CONNECTION_DRIVER_CLASS, System.getenv().get(HIBERNATE_CONNECTION_DRIVER_CLASS));
        }
        if (System.getenv().containsKey(HIBERNATE_DIALECT)) {
            hibernateMap.put(HIBERNATE_DIALECT, System.getenv().get(HIBERNATE_DIALECT));
        }
        return hibernateMap;
    }

    @Override
    public void close() throws Exception {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        if (!clazz.isAnnotationPresent(Stateless.class) && !clazz.isAnnotationPresent(Singleton.class)) {
            throw new IllegalArgumentException(clazz.getSimpleName() + " is not stateless or singleton, only stateless or sinlgeton beans allowed");
        }
        if (statelessBeans.containsKey(clazz)) {
            return (T) statelessBeans.get(clazz);
        }
        try {
            T bean = clazz.newInstance();
            statelessBeans.put(clazz, bean);

            Class<?> injectme = clazz;
            while (injectme != null) {
                inject(injectme, bean);
                handleRepository(injectme, bean);
                injectme = injectme.getSuperclass();
            }

            return bean;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> void handleRepository(Class<?> clazz, T bean) throws IllegalAccessException {
        if (clazz.getSimpleName().endsWith("Repository")) {
            try {
                Field field = clazz.getDeclaredField("em");
                field.setAccessible(true);
                field.set(bean, em);
            } catch (NoSuchFieldException e) {
                // ignore
            }
            try {
                Field field = clazz.getDeclaredField("sessionContext");
                field.setAccessible(true);
                field.set(bean, sessionContext);
            } catch (NoSuchFieldException e) {
                // ignore
            }
        }
    }

    private <T> void inject(Class<?> clazz, T bean) throws IllegalAccessException, ClassNotFoundException {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(EJB.class) || field.isAnnotationPresent(Inject.class)) {
                if (field.getType() == JMSContext.class) {
                    // ignore until we add JMS test support
                } else if (field.getType().isInterface()) {
                    String interfaceName = field.getType().getName();
                    String implName = "";
                    if (interfaceName.endsWith("Local")) {
                        implName = interfaceName.substring(0, interfaceName.length() - 5);
                    } else if (interfaceName.endsWith("Remote")) {
                        implName = interfaceName.substring(0, interfaceName.length() - 6);
                    }
                    field.setAccessible(true);
                    field.set(bean, getBean(Class.forName(implName)));
                } else {
                    field.setAccessible(true);
                    field.set(bean, getBean(field.getType()));
                }
            }
            if (field.isAnnotationPresent(Resource.class) && field.getType().equals(SessionContext.class)) {
                field.setAccessible(true);
                field.set(bean, sessionContext);
            }
        }
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public SessionContext getSessionContext() {
        return sessionContext;
    }

}
