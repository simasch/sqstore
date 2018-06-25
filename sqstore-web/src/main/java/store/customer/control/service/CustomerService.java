package store.customer.control.service;

import org.apache.log4j.Logger;
import store.customer.control.repository.CustomerRepository;
import store.customer.entity.Customer;
import store.customer.entity.CustomerInfoDTO;
import store.common.interceptor.MethodTraceInterceptor;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * As business service should contain the business logic.
 * In this example we don't have business logic but we don't want to break our showcase architecture.
 * <p>
 * This is a {@link Stateless} EJB. And EJB has a default {@link javax.ejb.TransactionAttribute} REQUIRED.
 * That means when a method is called with a transaction the method will participate in this transaction.
 * If there is no transaction a new transaction will be started.
 * <p>
 * The repository is decorated with an {@see Interceptors}.
 * Interceptors are called when a public EJB method is called and allows to intercept the call.
 * {@see MethodTraceInterceptor} for an example.
 */
@Stateless
@Interceptors({MethodTraceInterceptor.class})
public class CustomerService {

    private final static Logger LOGGER = Logger.getLogger(CustomerService.class);

    /**
     * We need the {@link JMSContext} to send messages.
     */
    @Inject
    private JMSContext context;

    /**
     * The queue is injected from JNDI using {@link Resource} and the appropriate JNDI name configured
     * in standalone-full.xml
     */
    @Resource(mappedName = "java:/jms/queue/CustomerQueue")
    private Queue queue;

    /**
     * We need the repository for data access
     */
    @Inject
    private CustomerRepository customerRepository;

    /**
     * We don't need any transactional behavior here
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public void tick() {
        LOGGER.info("Time is " + LocalDateTime.now().toString());
    }

    /**
     * To improve the readability of the code it's good style to add Async to an {@link Asynchronous} method
     * <p>
     * We don't need any transactional behavior here
     */
    @Asynchronous
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public void tickAsync() {
        tick();
    }

    /**
     * CDI Observer method that consumes events of type {@link Customer}
     * <p>
     * By default oberserver methods are called synchronous. With {@link Asynchronous} we can have this
     * method executed asynchrous which make sense when the method takes a while.
     * <p>
     * <strong>But be aware that {@link Asynchronous} methods are called in it's own transaction!</strong>
     *
     * @param customer {@link Customer}
     */
    @Asynchronous
    public void consumerCreatedAsync(@Observes Customer customer) {
        LOGGER.info("Consumer created event received: " + customer.getName());
    }

    /**
     * This method sends a {@see javax.jms.TextMessage} to the CustomerQueue.
     *
     * @param message
     */
    public void sendMessage(String message) {
        context.createProducer().send(queue, message);
    }

    /**
     * {@see CustomerRepository}
     *
     * @return list of {@link CustomerInfoDTO}
     */
    public List<CustomerInfoDTO> findAllCustomers() {
        return customerRepository.findAllDTOs();
    }

    /**
     * {@see CustomerRepository}
     *
     * @return {@link CustomerInfoDTO}
     */
    public Optional<Customer> findCustomerByName(String name) {
        return customerRepository.findCustomerByName(name);
    }

    /**
     * {@see CustomerRepository}
     *
     * @param customer
     */
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    /**
     * {@see CustomerRepository}
     *
     * @param id
     * @return optional {@link Customer}
     */
    public Optional<Customer> findCustomerById(Integer id) {
        return customerRepository.findById(id);
    }
}
