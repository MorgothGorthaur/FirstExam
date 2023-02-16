package exam.exception;

public class ManufacturerValidationException extends IllegalArgumentException {
    public ManufacturerValidationException() {
        super("name and country must be not empty!");
    }
}
