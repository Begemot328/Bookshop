package by.epam.bookshop.exceptions;

public class FactoryException extends ProjectException {

    public FactoryException(String message) {
        super(message);
    }

    public FactoryException(String message, Exception e) {
        super(message, e);
    }

    public FactoryException(Exception e) {
        super(e);
    }
}
