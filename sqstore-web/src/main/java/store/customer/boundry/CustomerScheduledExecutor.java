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

    @PostConstruct
    public void init() {
        managedScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    CustomerService customerService =
                            InitialContext.doLookup("java:module/" + CustomerService.class.getSimpleName());
                    customerService.tick();
                } catch (NamingException e) {
                    LOGGER.warn(e.getMessage(), e);
                }
            }
        }, 10, 10, TimeUnit.SECONDS);
    }

}
