package store.order.entity;

import store.common.entity.JpaEntity;
import store.inventory.entity.Product;

import javax.persistence.*;

@Entity
public class OrderItem extends JpaEntity {

    private Integer amount;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
