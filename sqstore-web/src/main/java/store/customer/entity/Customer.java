package store.customer.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

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
public class Customer {

    public static final String FIND_ALL = "Customer.findAll";
    public static final String FIND_ALL_AS_DTO = "Customer.findDtoAll";
    public static final String FIND_BY_NAME = "Customer.findByName";
    public static final String FIND_BY_NAME_AS_DTO = "Customer.findDtoByName";
    public static final String NAME = "name";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String name;

    @Embedded
    private Address address;

    @Embedded
    private CreditCard creditCard;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
