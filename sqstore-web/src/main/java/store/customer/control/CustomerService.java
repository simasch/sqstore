package store.customer.control;

import org.apache.log4j.Logger;
import store.customer.boundry.CustomerTimer;

import javax.ejb.Stateless;
import java.time.LocalDateTime;

@Stateless
public class CustomerService {

    private final static Logger LOGGER = Logger.getLogger(CustomerService.class);

    public void tick() {
        LOGGER.info("Time is " + LocalDateTime.now().toString());
    }
}
