package store.customer.boundry;

import store.customer.control.CustomerRepository;
import store.customer.entity.CustomerInfoDTO;

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
public class CustomerResource {

    @Inject
    private CustomerRepository customerRepository;

    @GET
    public List<CustomerInfoDTO> getCustomers() {
        return customerRepository.findCustomerInfoDTOs("Peter Muster");
    }
}
