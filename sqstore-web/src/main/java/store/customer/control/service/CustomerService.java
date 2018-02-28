package store.customer.control.service;

import org.apache.log4j.Logger;
import store.customer.control.repository.CustomerRepository;
import store.customer.entity.Customer;
import store.customer.entity.CustomerInfoDTO;
import store.interceptor.MethodTraceInterceptor;

import javax.annotation.Resource;
import javax.cache.annotation.CacheResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jms.JMSContext;
import javax.jms.Queue;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Stateless
@Interceptors({MethodTraceInterceptor.class})
public class CustomerService {

    private final static Logger LOGGER = Logger.getLogger(CustomerService.class);

    @Inject
    private JMSContext context;

    @Resource(mappedName = "java:/jms/queue/CustomerQueue")
    private Queue queue;

    @Inject
    private CustomerRepository customerRepository;

    public void tick() {
        LOGGER.info("Time is " + LocalDateTime.now().toString());
    }

    @Asynchronous
    public void tickAsync() {
        tick();
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

    public Optional<Customer> findCustomerByName(String name) {
        return customerRepository.findCustomerByName(name);
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer findCustomerById(Integer id) {
        return customerRepository.findById(id);
    }
}
