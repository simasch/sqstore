package store.customer.entity;

import javax.xml.bind.annotation.XmlRootElement;

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
