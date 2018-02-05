package store.customer.boundry;

import store.customer.control.CustomerRepository;
import store.customer.control.CustomerMessageSender;
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
    private CustomerRepository customerRepository;
    @Inject
    private CustomerMessageSender customerMessageSender;
    @Inject
    private Event<Customer> customerEvent;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CustomerInfoDTO> getCustomers() {
        return customerRepository.findAllDTOs();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Customer> getCustomersAsXml() {
        return customerRepository.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);

        customerMessageSender.sendMessage("Customer created: " + customer.getName());

        customerEvent.fire(customer);
        customerEvent.fire(customer);
    }
}
