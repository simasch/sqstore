package store.customer.control;

import store.customer.entity.Customer;
import store.customer.entity.CustomerInfoDTO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CustomerRepository {

    private EntityManager em;

    public CustomerRepository(EntityManager em) {
        this.em = em;
    }

    public List<Customer> findCustomerByName(String name) {
        TypedQuery<Customer> query = em.createQuery("select c from Customer c where c.name = :name", Customer.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    public List<CustomerInfoDTO> findCustomerInfoDTOs(String name) {
        TypedQuery<CustomerInfoDTO> query = em.createQuery("select new store.customer.entity.CustomerInfoDTO(c.id, c.name) " +
                "from Customer c where c.name = :name", CustomerInfoDTO.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
}
