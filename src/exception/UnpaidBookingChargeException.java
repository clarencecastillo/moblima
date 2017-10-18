package exception;

public class UnpaidBookingChargeException extends Exception {
    public UnpaidBookingChargeException() {
        super("Booking charge is unpaid");
    }
}
