package model.transaction;

import java.util.Date;
import java.util.UUID;
import model.commons.Entity;

public class Payment extends Entity {

    protected PaymentStatus status;
    protected Payable payable;
    protected Date date;

    public Payment(PaymentStatus status, Payable payable) {
        this.status = status;
        this.payable = payable;
        this.date = new Date();
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Payable getPayable() {
        return payable;
    }

    public Date getDate() {
        return date;
    }

    public void setPayable(Payable payable) {
        this.payable = payable;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
