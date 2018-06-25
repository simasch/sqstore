package store.customer.boundary;

import store.customer.control.service.CustomerService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

/**
 * Calling methods in intervals is not a good idea to do with EJB timer service.
 * The EJB timer service can not handle time shift because it always stores the last execution timestamp.
 * <p>
 * So EJB timers are a good choice when you want to execute methods on a datetime like every day at 2 am.
 * <p>
 * The executor is a {@link Singleton} and exists only once per application server and as a {@link Startup} EJB
 * it's started when the application is deployed and started.
 * <p>
 * For intervals the {@link ManagedScheduledExecutorService} is the right choice.
 * <p>
 * <strong>HINT!</strong>
 * You should use Singletons with caution because they could lead to locks and waits.
 * The default {@link javax.ejb.LockType} is WRITE and therefor all the method calls are serialized.
 */
@Startup
@Singleton
public class CustomerScheduledExecutor {

    /**
     * We get the {@link ManagedScheduledExecutorService} from the app server as resource.
     * The difference between @Resource and @Inject is that @Resource gets dependencies by name from
     * JNDI (Java Naming and Directory Interface) and @Inject gets dependencies by type.
     */
    @Resource
    private ManagedScheduledExecutorService managedScheduledExecutorService;

    /**
     * The above is not completely true when it comes to EJBs. EJBs are always injected by name form JNDI!
     * <p>
     * It's good design to extract business code to a business service and let the executor call this methods.
     */
    @Inject
    private CustomerService customerService;

    /**
     * This method is called after startup and instantiation of the singleton EJB.
     * <p>
     * The work is delegated to the {@link CustomerService} synchronous tick method.
     * This method doesn't have to be asynchronous because it's already called in it's own thread.
     */
    @PostConstruct
    public void init() {
        managedScheduledExecutorService.scheduleAtFixedRate(customerService::tick,
                0, 20, TimeUnit.SECONDS);
    }

}
