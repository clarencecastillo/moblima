package model.transaction;

public interface Payable extends Priceable {
    Payment getPayment();
    void setPayment(Payment payment);
}
