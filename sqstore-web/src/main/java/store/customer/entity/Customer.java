package store.customer.entity;

import store.common.entity.JpaEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * {@link XmlRootElement} is used for XML serialization with JAXB (Java XML Binding).
 * <p>
 * {@link Entity} represents a database table.
 * <p>
 * {@link NamedQuery} will create PreparedStatements at runtime and are usually performance gain.
 */
@XmlRootElement
@Entity
@NamedQueries(
        {
                @NamedQuery(name = Customer.FIND_ALL, query = "select c from Customer c order by c.name"),
                @NamedQuery(name = Customer.FIND_ALL_AS_DTO, query = "select new store.customer.entity.CustomerInfoDTO(c.id, c.name) from Customer c order by c.name"),
                @NamedQuery(name = Customer.FIND_BY_NAME, query = "select c from Customer c where c.name = :" + Customer.NAME + " order by c.name"),
                @NamedQuery(name = Customer.FIND_BY_NAME_AS_DTO, query = "select new store.customer.entity.CustomerInfoDTO(c.id, c.name) from Customer c where c.name = :" + Customer.NAME + " order by c.name")
        }
)
public class Customer extends JpaEntity {

    /**
     * It's good style to define constants for the names of the queries and the parameters.
     */
    public static final String FIND_ALL = "Customer.findAll";
    public static final String FIND_ALL_AS_DTO = "Customer.findDtoAll";
    public static final String FIND_BY_NAME = "Customer.findByName";
    public static final String FIND_BY_NAME_AS_DTO = "Customer.findDtoByName";
    public static final String NAME = "name";

    /**
     * We want to have a not null and a unique constraint for the database table column name.
     */
    @NotNull
    @Column(unique = true)
    private String name;

    /**
     * Here we use the {@link Embeddable}
     */
    @Embedded
    private Address address;

    /**
     * Here we use the {@link Embeddable}
     */
    @Embedded
    private CreditCard creditCard;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
