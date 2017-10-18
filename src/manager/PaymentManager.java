package manager;

import exception.InvalidPayableException;
import model.transaction.Payable;
import model.transaction.Payment;
import model.transaction.PaymentStatus;

public class PaymentManager extends EntityManager<Payment> {

    private static PaymentManager instance = new PaymentManager();

    private PaymentManager() {
        super();
    }

    public static PaymentManager getInstance() {
        return instance;
    }

    public Payment makePayment(Payable payable) throws InvalidPayableException{

        // Assume Payment will always be successful.
        Payment payment = new Payment(PaymentStatus.ACCEPTED, payable);

        PaymentManager paymentManager = PaymentManager.getInstance();

        if (!payable.isPendingPayment())
            throw new InvalidPayableException();

        payable.setPayment(payment);
        entities.put(payment.getId(), payment);
        return payment;
    }
}
