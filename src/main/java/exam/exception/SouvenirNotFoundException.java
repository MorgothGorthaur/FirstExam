package exam.exception;

public class SouvenirNotFoundException extends RuntimeException {
    public SouvenirNotFoundException(Long id) {
        super("souvenir with id=" + id + " not founded!");
    }
}
