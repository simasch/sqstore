package store.customer.boundry;

import store.customer.control.CustomerRepository;
import store.customer.control.CustomerMessageSender;
import store.customer.control.CustomerService;
import store.customer.entity.Customer;
import store.customer.entity.CustomerInfoDTO;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("customers")
@Stateless
public class CustomerResource {

    @Inject
    private CustomerService customerService;
    @Inject
    private Event<Customer> customerEvent;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CustomerInfoDTO> getCustomers() {
        return customerService.findAllCustomers();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<CustomerInfoDTO> getCustomersAsXml() {
        return customerService.findAllCustomers();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addCustomer(Customer customer) {
        customerService.saveCustomer(customer);

        customerService.sendMessage("Customer created: " + customer.getName());

        customerEvent.fire(customer);
    }
}
