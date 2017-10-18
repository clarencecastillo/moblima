package exception;

public class InputOutOfBoundsException extends Exception {

    private Object outOfBoundsInput;
    public InputOutOfBoundsException(Object outOfBoundsInput) {
        this.outOfBoundsInput = outOfBoundsInput;
    }

    public Object getOutOfBoundsInput() {
        return outOfBoundsInput;
    }
}
