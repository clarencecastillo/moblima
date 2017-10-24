package manager;

import exception.InvalidPayableException;
import exception.UninitialisedSingletonException;
import model.booking.Booking;
import model.booking.BookingStatus;
import model.transaction.Payable;
import model.transaction.Payment;
import model.transaction.PaymentStatus;

import java.util.Date;

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

    public Payment makePayment(Booking booking) throws InvalidPayableException{

        // Assume Payment will always be successful.
        Payment payment = new Payment(booking.getPrice());
        payment.setDate(new Date());
        payment.setStatus(PaymentStatus.ACCEPTED);
        payment.setTransactionId(booking);
        booking.setPayment(payment);
        entities.put(payment.getId(), payment);
        return payment;
    }
}
