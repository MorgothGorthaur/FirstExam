package exam.exception;

public class ManufacturedNotFoundException extends RuntimeException {
    public ManufacturedNotFoundException(long id) {
        super("manufacturer with id=" + id + " not founded!");
    }
}
