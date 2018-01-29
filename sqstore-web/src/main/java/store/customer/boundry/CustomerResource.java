package store.customer.boundry;

import store.customer.control.CustomerRepository;
import store.customer.entity.Customer;
import store.customer.entity.CustomerInfoDTO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class CustomerResource {

    @Inject
    private CustomerRepository customerRepository;

    @GET
    @Path("v1")
    public List<CustomerInfoDTO> getCustomerDtos() {
        return customerRepository.findCustomerInfoDTOByName("Peter Muster");
    }

    @GET
    @Path("v2")
    public List<Customer> getCustomers() {
        return customerRepository.findByName("Peter Muster");
    }

}
