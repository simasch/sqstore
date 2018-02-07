package store.customer.boundry;

import org.apache.log4j.Logger;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.time.LocalDateTime;

@Startup
@Singleton
public class CustomerTimer {

    private final static Logger LOGGER = Logger.getLogger(CustomerTimer.class);

    @Schedule(hour = "*", minute = "*", second = "*/10")
    public void tick() {
        LOGGER.info("Time is " + LocalDateTime.now().toString());
    }
}
