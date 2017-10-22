package manager;

import exception.InvalidPayableException;
import model.transaction.Payable;
import model.transaction.Payment;
import model.transaction.PaymentStatus;

public class PaymentController extends EntityController<Payment> {

    private static PaymentController instance = new PaymentController();

    private PaymentController() {
        super();
    }

    public static PaymentController getInstance() {
        return instance;
    }

    public Payment makePayment(Payable payable) throws InvalidPayableException{

        // Assume Payment will always be successful.
        Payment payment = payable.getPayment();

        if (!payable.isPendingPayment())
            throw new InvalidPayableException();

        payment.setStatus(PaymentStatus.ACCEPTED);
        entities.put(payment.getId(), payment);
        return payment;
    }
}
