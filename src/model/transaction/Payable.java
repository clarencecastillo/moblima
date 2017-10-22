package model.transaction;

public interface Payable {
    double getPrice();
    Payment getPayment();
    boolean isPendingPayment();
}
