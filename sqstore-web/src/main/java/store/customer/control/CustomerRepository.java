package store.customer.control;

import store.customer.entity.Customer;
import store.customer.entity.CustomerInfoDTO;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class CustomerRepository {

    @PersistenceContext
    private EntityManager em;

    // Default Constructur is used by the EJB container to instantiate this EJB
    public CustomerRepository() {
    }

    // This consturctor is only used for unit testing
    CustomerRepository(EntityManager em) {
        this.em = em;
    }

    public List<Customer> findCustomerByName(String name) {
        TypedQuery<Customer> q = em.createNamedQuery(Customer.FIND_BY_NAME, Customer.class);
        q.setParameter(Customer.NAME, name);
        return q.getResultList();
    }

    public List<CustomerInfoDTO> findCustomerInfoDTOs(String name) {
        TypedQuery<CustomerInfoDTO> query = em.createNamedQuery(Customer.FIND_BY_NAME_AS_DTO, CustomerInfoDTO.class);
        query.setParameter(Customer.NAME, name);
        return query.getResultList();
    }

    public List<Customer> findAll() {
        TypedQuery<Customer> query = em.createNamedQuery(Customer.FIND_ALL, Customer.class);
        return query.getResultList();
    }

    public List<CustomerInfoDTO> findAllDTOs() {
        TypedQuery<CustomerInfoDTO> query = em.createNamedQuery(Customer.FIND_ALL_AS_DTO, CustomerInfoDTO.class);
        return query.getResultList();
    }

    public void save(Customer customer) {
        if (customer.getId() == null) {
            em.persist(customer);
        } else {
            em.merge(customer);
        }
    }
}
