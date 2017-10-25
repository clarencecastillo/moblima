package manager;

import exception.InvalidPayableException;
import exception.UninitialisedSingletonException;
import model.transaction.Payable;
import model.transaction.Payment;
import model.transaction.PaymentStatus;

public class PaymentController extends EntityController<Payment> {

    private static PaymentController instance;

    private PaymentController() {
        super();
    }

    public static void init() {
        instance = new PaymentController();
    }

    public static PaymentController getInstance() {
        if (instance == null)
            throw new UninitialisedSingletonException();
        return instance;
    }

    public Payment makePayment(Payable payable) throws InvalidPayableException {

        // Assume Payment will always be successful.
        Payment payment = new Payment(payable.getPrice(), payable.getTransactionCode());
        payment.setStatus(PaymentStatus.ACCEPTED);
        payable.setPayment(payment);
        entities.put(payment.getId(), payment);
        return payment;
    }
}
