package store.customer.entity;

public class CustomerInfoDTO {

    private final Integer id;
    private final String name;

    public CustomerInfoDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
