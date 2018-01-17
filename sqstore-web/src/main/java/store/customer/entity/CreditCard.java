package store.customer.entity;

import javax.persistence.Embeddable;

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
