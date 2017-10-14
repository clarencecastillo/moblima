package model.transaction;

public interface Payable {
    double getPrice();
    void setPayment(Payment payment);
    Payment getPayment();
    boolean isPendingPayment();
}
