package by.epam.bookshop.exceptions;

public class ValidationException extends ProjectException {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Exception e) {
        super(message, e);
    }

    public ValidationException(Exception e) {
        super(e);
    }
}
