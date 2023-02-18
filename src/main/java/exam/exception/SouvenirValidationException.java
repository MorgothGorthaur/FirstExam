package exam.exception;

public class SouvenirValidationException extends IllegalArgumentException {
    public SouvenirValidationException() {
        super("name must be not empty, date must be not after today`s date and price must be higher then 0");
    }
}
