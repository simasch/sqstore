package store.inventory.control;

import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import store.inventory.entity.Product;

import java.util.List;

@Repository
public abstract class ProductRepository extends AbstractFullEntityRepository<Product, String> {

    public List<Product> findByNumer(String number) {
        return typedQuery("select p from Product p where p.number = ?1")
                .setParameter(1, number)
                .getResultList();
    }

}
