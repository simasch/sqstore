package store.customer.entity;

import javax.persistence.Embeddable;

/**
 * Using {@link Embeddable} it's possible to map the content of one database table to multiple classes.
 * In contrast to {@link javax.persistence.Entity} {@link Embeddable} don't have it's own identity.
 */
@Embeddable
public class Address {

    private String street;
    private String houseNumber;
    private String zip;
    private String city;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
