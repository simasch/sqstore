package store.customer.control;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import store.customer.entity.Customer;
import store.customer.entity.CustomerInfoDTO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public interface CustomerRepository extends EntityRepository<Customer, Integer> {

    List<Customer> findByName(String name);

    @Query("select new store.customer.entity.CustomerInfoDTO(c.id, c.name) from Customer c where c.name = ?1")
    List<CustomerInfoDTO> findCustomerInfoDTOByName(String name);

}
