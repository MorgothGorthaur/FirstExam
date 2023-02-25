package exam.exception;

public class SouvenirValidationException extends IllegalArgumentException {
    public SouvenirValidationException() {
        super("date must be not after today`s date and price must be >= 0");
    }
}
