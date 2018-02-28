package store.customer.control.repository;

import store.customer.entity.Customer;
import store.customer.entity.CustomerInfoDTO;
import store.interceptor.MethodTraceInterceptor;

import javax.cache.annotation.CacheResult;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * This repository takes care of the Customer entity.
 * <p>
 * A repository is responsible for retrieving and storing data from a data source.
 * Using repositories separates these data retrieval concerns from the business logic and makes the data
 * access logic testable.
 * <p>
 * The repository is decorated with an {@see Interceptors}.
 * Interceptors are called when a public EJB method is called and allows to intercept the call.
 * {@see MethodTraceInterceptor} for an example.
 */
@Stateless
@Interceptors({MethodTraceInterceptor.class})
public class CustomerRepository {

    /**
     * To get access to the {@link EntityManager} we use {@link PersistenceContext} annotation.
     * <p>
     * If there is only one persistence unit (e.g. persistence.xml) we don't need to declare a name.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Default constructor is used by the EJB container to instantiate this EJB
     */
    public CustomerRepository() {
    }

    /**
     * This constructor is only used for unit testing
     *
     * @param em the {@link EntityManager}
     */
    CustomerRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * Searches a customer by name.
     * <p>
     * This method returns an {@link Optional} of type customer because it's possible that a customer with the given name
     * doesn't exists.
     * It's a better style to return {@see Optional} when the result can be null.
     * If we would simply return a {@link Customer} the consumer would not see that this can be null and this
     * could leed to a {@link java.lang.NullPointerException}
     *
     * @param name
     * @return {@link Optional}
     */
    public Optional<Customer> findCustomerByName(String name) {
        TypedQuery<Customer> q = em.createNamedQuery(Customer.FIND_BY_NAME, Customer.class);
        q.setParameter(Customer.NAME, name);
        List<Customer> list = q.getResultList();
        if (list.isEmpty()) {
            return Optional.empty();
        } else if (list.size() == 1) {
            return Optional.of(list.get(0));
        } else {
            throw new IllegalStateException("More than one customer found!");
        }
    }

    /**
     * Returns a customer with a given name.
     * <p>
     * We return DTOs because we only want to load the minimal data from database.
     * DTO is directly created from the result using the Constructor Expression
     * The query is defined as named query on the Customer entity {@see Customer}
     *
     * @param name
     * @return list of {@link CustomerInfoDTO}
     */
    public List<CustomerInfoDTO> findCustomerInfoDTOs(String name) {
        TypedQuery<CustomerInfoDTO> query = em.createNamedQuery(Customer.FIND_BY_NAME_AS_DTO, CustomerInfoDTO.class);
        query.setParameter(Customer.NAME, name);
        return query.getResultList();
    }


    /**
     * Returns all customers.
     * <p>
     * We return DTOs because we only want to load the minimal data from database.
     * DTO is directly created from the result using the Constructor Expression
     * The query is defined as named query on the Customer entity {@see Customer}
     *
     * @return list of {@link CustomerInfoDTO}
     */
    public List<CustomerInfoDTO> findAllDTOs() {
        TypedQuery<CustomerInfoDTO> query = em.createNamedQuery(Customer.FIND_ALL_AS_DTO, CustomerInfoDTO.class);
        return query.getResultList();
    }

    @CacheResult(cacheName = "customer-cache")
    public Customer findById(Integer id) {
        return em.find(Customer.class, id);
    }

    /**
     * Either insert or update the passed customer.
     * <p>
     * It's important to distinguish between persist and merge.
     * Especially if you want to change the customer after save because merge will return a proxy object of the
     * customer and you MUST use that one. Never use the object that you pass to merge!
     *
     * @param customer
     * @return {@link Customer}
     */
    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            em.persist(customer);
        } else {
            customer = em.merge(customer);
        }
        return customer;
    }
}
