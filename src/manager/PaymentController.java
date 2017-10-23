package manager;

import exception.InvalidPayableException;
import model.booking.Booking;
import model.booking.BookingStatus;
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

    public Payment makePayment(Booking booking) throws InvalidPayableException{

        // Assume Payment will always be successful.
        Payment payment = new Payment(booking.getPrice());
        payment.setStatus(PaymentStatus.ACCEPTED);
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setPayment(payment);
        entities.put(payment.getId(), payment);
        return payment;
    }
}
