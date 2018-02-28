package store.customer.boundary;

import store.customer.control.service.CustomerService;
import store.customer.entity.Customer;
import store.customer.entity.CustomerInfoDTO;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * REST resource that provides access to customer data.
 * <p>
 * It's defined as {@link Stateless} EJB.
 * The reason is that we want this resource to be the transaction boundary.
 * So all the work done in one method of this resource will be handled by the same transaction.
 * <p>
 * The resource shouldn't contain business logic only logic to call the business services and take care
 * of message marshalling and unmarshalling and translating exceptions to
 */
@Path("customers")
@Stateless
public class CustomerResource {

    /**
     * CustomerService to delegate business logic
     */
    @Inject
    private CustomerService customerService;

    /**
     * CDI Event to produce events.
     * CDI Events are great to decouple components but should be used with care because observers run
     * synchronous and there is no way to check if the event is really consumed when doing static code analysis.
     */
    @Inject
    private Event<Customer> customerEvent;

    /**
     * Returns a list of customer DTOs in JSON format.
     * <p>
     * You should always return DTOs instead of entities when the data is used read only.
     * This style will increase the performance of your application
     *
     * @return list of {@link CustomerInfoDTO}
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CustomerInfoDTO> getCustomers() {
        return customerService.findAllCustomers();
    }

    /**
     * Returns a list of customer DTOs in XML format.
     * <p>
     * You should always return DTOs instead of entities when the data is used read only.
     * This style will increase the performance of your application
     *
     * @return list of {@link CustomerInfoDTO}
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<CustomerInfoDTO> getCustomersAsXml() {
        return customerService.findAllCustomers();
    }

    /**
     * Returns a customer based on the path param. e.g. /api/customer/1 returns the customer with id 1.
     * <p>
     * Here it's ok to use the entity as return value because you probably need all the information to display
     * and maybe to update.
     *
     * @return {@link Customer}
     */
    @GET
    @Path("{id}")
    public Customer getCustomerByName(@PathParam("id") Integer id) {
        return customerService.findCustomerById(id);
    }

    /**
     * The difference between POST and PUT is that PUT should behave idempotent = SQL UPDATE
     * POST is more like SQL INSERT
     *
     * Here we don't want to distinguish between UPDATE and INSERT so POST is ok.
     *
     * The customer object is passed as JSON data and unmarshalled by the JSON processor
     *
     * @param customer
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addCustomer(Customer customer) {
        customerService.saveCustomer(customer);

        customerService.sendMessage("Customer created: " + customer.getName());

        customerEvent.fire(customer);
    }
}
