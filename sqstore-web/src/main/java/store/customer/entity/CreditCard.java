package store.customer.entity;

import javax.persistence.Embeddable;

/**
 * Using {@link Embeddable} it's possible to map the content of one database table to multiple classes.
 * In contrast to {@link javax.persistence.Entity} {@link Embeddable} don't have it's own identity.
 */
@Embeddable
public class CreditCard {

    private String number;
    private String validTo;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }
}
