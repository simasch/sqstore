package store.product.control;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import store.customer.control.CustomerRepository;
import store.customer.entity.Customer;
import store.customer.entity.CustomerInfoDTO;
import store.inventory.control.ProductRepository;
import store.inventory.entity.Product;

import javax.inject.Inject;
import java.util.List;

@RunWith(CdiTestRunner.class)
public class ProductRepositoryTest {

    @Inject
    private ProductRepository productRepository;

    @Test
    public void findByNumber() {
        List<Product> list = productRepository.findByNumer("123");

        Assert.assertEquals(0, list.size());
    }
}
