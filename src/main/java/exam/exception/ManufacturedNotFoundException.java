package exam.exception;

public class ManufacturedNotFoundException extends RuntimeException {
    public ManufacturedNotFoundException(Long id) {
        super("manufacturer with id=" + id + " not founded!");
    }
}
