package store.customer.boundry;

import org.apache.log4j.Logger;
import store.customer.entity.Customer;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

@Stateless
public class CustomerEventConsumer {

    private final static Logger LOGGER = Logger.getLogger(CustomerEventConsumer.class);

    @Asynchronous
    public void onCustomerEvent(@Observes Customer customer) throws InterruptedException {
        LOGGER.info("Start " + customer.getName());
        Thread.sleep(10000);
        LOGGER.info("End   " + customer.getName());
    }

}
