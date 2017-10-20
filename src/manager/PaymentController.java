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
        Payment payment = new Payment(PaymentStatus.ACCEPTED, payable);

        PaymentController paymentManager = PaymentController.getInstance();

        if (!payable.isPendingPayment())
            throw new InvalidPayableException();

        payable.setPayment(payment);
        entities.put(payment.getId(), payment);
        return payment;
    }
}
