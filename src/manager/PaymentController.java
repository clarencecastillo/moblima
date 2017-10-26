package manager;

import exception.UninitialisedSingletonException;
import model.transaction.Payable;
import model.transaction.Payment;
import model.transaction.PaymentStatus;

/**
 Represents the controller of payments.
 @author Castillo Clarence Fitzgerald Gumtang
 @version 1.0
 @since 2017-10-20
 */
public class PaymentController extends EntityController<Payment> {

    /**
     * A reference to this singleton instance.
     */
    private static PaymentController instance;

    /**
     * Creates the payment controller.
     */
    private PaymentController() {
        super();
    }

    /**
     * Initialize the payment controller.
     */
    public static void init() {
        instance = new PaymentController();
    }

    /**
     * Gets this Payment Controller's singleton instance.
     * @return this Payment Controller's singleton instance.
     */
    public static PaymentController getInstance() {
        if (instance == null)
            throw new UninitialisedSingletonException();
        return instance;
    }

    /**
     * Creates a payment when a payable is paid for. The amount of the payment is the price of the payable.
     * The transaction code is given by the payable. The transaction The assumption is that the payment is
     * always successful, so the status of the payment is set to be accepted. The payable will be set with
     * this payment and the payment will be put into entities.
     * @param payable The payable whose payment is to be made.
     * @return the payment for this payable.
     */
    public Payment makePayment(Payable payable) {

        Payment payment = new Payment(payable.getPrice(), payable.getTransactionCode());
        payment.setStatus(PaymentStatus.ACCEPTED);
        payable.setPayment(payment);
        entities.put(payment.getId(), payment);
        return payment;
    }
}
