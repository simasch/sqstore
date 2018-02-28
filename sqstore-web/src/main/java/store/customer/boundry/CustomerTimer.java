package store.customer.boundry;

import store.customer.control.CustomerService;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class CustomerTimer {

    @EJB
    private CustomerService customerService;

    @Schedule(hour = "*", minute = "*", second = "*/7")
    public void tick() {
        customerService.tick();
    }
}
