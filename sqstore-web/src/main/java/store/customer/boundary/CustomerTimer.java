package store.customer.boundary;

import store.customer.control.service.CustomerService;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * EJB timers are a good choice when you want to execute methods on a datetime like every day at 2 am.
 * But they could lead to problems when your server has time changes as the timer service stores the last
 * execution time and calculates the next execution based on this time.
 * <p>
 * It's a {@link Singleton} and exists only once per application server and as a {@link Startup} EJB
 * it's started when the application is deployed and started.
 * <p>
 * How to handle intervals {@see store.customer.boundary.CustomerScheduledExecutor}
 * <p>
 * <strong>HINT!</strong>
 * You should use Singletons with caution because they could lead to locks and waits.
 * The default {@link javax.ejb.LockType} is WRITE and therefor all the methods calls are serialized.
 */
@Startup
@Singleton
public class CustomerTimer {

    @Inject
    private CustomerService customerService;

    /**
     * {@link Schedule} has a cron-like syntax
     * <p>
     * Here we use the asynchronous tick method to don't block the singleton EJB method.
     */
    @Schedule(hour = "*", minute = "0/30")
    public void tick() {
        customerService.tickAsync();
    }
}
