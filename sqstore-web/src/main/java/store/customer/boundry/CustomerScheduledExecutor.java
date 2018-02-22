package store.customer.boundry;

import org.apache.log4j.Logger;
import store.customer.control.CustomerService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.concurrent.TimeUnit;

@Startup
@Singleton
public class CustomerScheduledExecutor {

    private final static Logger LOGGER = Logger.getLogger(CustomerService.class);

    @Resource
    private ManagedScheduledExecutorService managedScheduledExecutorService;

    @EJB
    private CustomerService customerService;

    @PostConstruct
    public void init() {
        managedScheduledExecutorService.scheduleAtFixedRate(customerService::tick, 10, 10, TimeUnit.SECONDS);
    }

}
