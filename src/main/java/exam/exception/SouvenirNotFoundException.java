package exam.exception;

public class SouvenirNotFoundException extends RuntimeException {
    public SouvenirNotFoundException(long id) {
        super("souvenir with id=" + id + " not founded!");
    }
}
