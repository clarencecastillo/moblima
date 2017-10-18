package exception;

import java.util.InputMismatchException;

public class InputUnrecognisedException extends InputMismatchException {

    private Object mismatchInput;

    public InputUnrecognisedException(Object mismatchInput) {
        this.mismatchInput = mismatchInput;
    }

    public Object getMismatchInput() {
        return mismatchInput;
    }
}
