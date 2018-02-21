package store.customer.boundry;

import store.customer.control.CustomerService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Startup
@Singleton
public class CustomerScheduledExecutor {


    @Resource
    private ManagedScheduledExecutorService managedScheduledExecutorService;

    @EJB
    private CustomerService customerService;

    @PostConstruct
    public void init() {
        managedScheduledExecutorService.scheduleAtFixedRate(customerService::tick, 3, 7, TimeUnit.SECONDS);
    }

}
