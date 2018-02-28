package store.customer.control;

import org.apache.log4j.Logger;
import store.customer.boundry.CustomerTimer;
import store.customer.entity.Customer;
import store.customer.entity.CustomerInfoDTO;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class CustomerService {

    private final static Logger LOGGER = Logger.getLogger(CustomerService.class);

    @Inject
    private JMSContext context;

    @Resource(mappedName = "java:/jms/queue/CustomerQueue")
    private Queue queue;

    @EJB
    private CustomerRepository customerRepository;

    public void tick() {
        LOGGER.info("Time is " + LocalDateTime.now().toString());
    }

    @Asynchronous
    public void consumerCreated(@Observes Customer customer) throws InterruptedException {
        LOGGER.info("Consumer created event received: " + customer.getName());
    }

    public void sendMessage(String message) {
        context.createProducer().send(queue, message);
    }

    public List<CustomerInfoDTO> findAllCustomers() {
        return customerRepository.findAllDTOs();
    }

    public Customer findCustomerByName(String name) {
        return customerRepository.findCustomerByName(name);
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

}
