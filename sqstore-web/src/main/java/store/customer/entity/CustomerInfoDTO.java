package store.customer.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A DTO represents a data container.
 * See Martin Fowlers enterprise pattern: https://martinfowler.com/eaaCatalog/dataTransferObject.html
 */
@XmlRootElement
public class CustomerInfoDTO {

    private Integer id;
    private String name;

    public CustomerInfoDTO() {
    }

    public CustomerInfoDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

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
