package store.test.context;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import net.ttddyy.dsproxy.listener.ChainListener;
import net.ttddyy.dsproxy.listener.DataSourceQueryCountListener;
import net.ttddyy.dsproxy.listener.logging.DefaultQueryLogEntryCreator;
import net.ttddyy.dsproxy.listener.logging.QueryLogEntryCreator;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.hibernate.engine.jdbc.internal.Formatter;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.jpa.boot.spi.IntegratorProvider;
import store.test.context.hibernate.DataSourceProvider;
import store.test.context.hibernate.Database;
import store.test.context.hibernate.PersistenceUnitInfoForTest;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The ApplicationContext provides basic dependency injection for EJBs and CDI Beans.
 * <p>
 * Everything is a singleton.
 */
public class ApplicationContext implements AutoCloseable, Serializable {

    private static final long serialVersionUID = 1L;

    private EntityManagerFactory emf;
    private final EntityManager em;
    private final DummySessionContext sessionContext;
    private final Map<Class<?>, Object> statelessBeans;
    private String entityBasePackage;


    @SuppressWarnings({"rawtypes", "unchecked"})
    public ApplicationContext(String entityBasePackage) {
        this.entityBasePackage = entityBasePackage;
        this.statelessBeans = new HashMap();
        sessionContext = new DummySessionContext();

        PersistenceUnitInfo persistenceUnitInfo = persistenceUnitInfo(getClass().getSimpleName());

        Map<String, Object> configuration = new HashMap<>();

        Integrator integrator = integrator();
        if (integrator != null) {
            configuration.put("hibernate.integrator_provider", (IntegratorProvider) () -> Collections.singletonList(integrator));
        }

        emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(persistenceUnitInfo, configuration);
        em = emf.createEntityManager();
    }

    @Override
    public void close() {
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

    protected PersistenceUnitInfoForTest persistenceUnitInfo(String name) {
        PersistenceUnitInfoForTest persistenceUnitInfo = new PersistenceUnitInfoForTest(name, entityClassNames(), properties());

        String[] resources = resources();
        if (resources != null) {
            persistenceUnitInfo.getMappingFileNames().addAll(Arrays.asList(resources));
        }

        return persistenceUnitInfo;
    }

    protected Class<?>[] entities() {
        List<Class<?>> classes = new ArrayList<>();
        new FastClasspathScanner(entityBasePackage)
                .matchClassesWithAnnotation(Entity.class, c -> {
                    classes.add(c);
                })
                .scan();

        return classes.toArray(new Class<?>[classes.size()]);
    }

    protected String[] resources() {
        return null;
    }

    protected List<String> entityClassNames() {
        return Arrays.asList(entities())
                .stream()
                .map(Class::getName)
                .collect(Collectors.toList());
    }

    protected Properties properties() {
        Properties properties = new Properties();
        DataSource dataSource = dataSource();
        if (dataSource != null) {
            properties.put("hibernate.connection.datasource", dataSource);
        }
        properties.put("hibernate.dialect", dataSourceProvider().hibernateDialect());
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.generate_statistics", Boolean.FALSE.toString());
        return properties;
    }

    protected DataSource dataSource() {
        QueryLogEntryCreator logEntryCreator = new DefaultQueryLogEntryCreator() {
            @Override
            protected String formatQuery(String query) {
                return FormatStyle.BASIC.getFormatter().format(query);  // use Hibernte formatter
            }
        };

        ChainListener listener = new ChainListener();
        SLF4JQueryLoggingListener loggingListener = new SLF4JQueryLoggingListener();
        loggingListener.setQueryLogEntryCreator(logEntryCreator);
        listener.addListener(loggingListener);
        listener.addListener(new DataSourceQueryCountListener());
        return ProxyDataSourceBuilder
                .create(dataSourceProvider().dataSource())
                .name("DATA_SOURCE_PROXY")
                .listener(listener)
                .multiline()
                .build();
    }

    protected DataSourceProvider dataSourceProvider() {
        return database().dataSourceProvider();
    }

    protected Database database() {
        return Database.HSQLDB;
    }

    protected Integrator integrator() {
        return null;
    }

    private static class PrettyQueryEntryCreator extends DefaultQueryLogEntryCreator {
        private Formatter formatter = FormatStyle.BASIC.getFormatter();

        @Override
        protected String formatQuery(String query) {
            return this.formatter.format(query);
        }
    }
}
