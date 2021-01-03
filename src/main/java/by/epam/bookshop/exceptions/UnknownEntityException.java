package by.epam.bookshop.exceptions;

public class UnknownEntityException extends ProjectException {

    public UnknownEntityException(String message) {
        super(message);
    }

    public UnknownEntityException(String message, Exception e) {
        super(message, e);
    }

    public UnknownEntityException(Exception e) {
        super(e);
    }
}
