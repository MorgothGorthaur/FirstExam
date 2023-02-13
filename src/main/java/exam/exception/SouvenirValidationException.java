package exam.exception;

public class SouvenirValidationException extends IllegalArgumentException {
    public SouvenirValidationException() {
        super("name must be not empty and price must be higher then 0");
    }
}
