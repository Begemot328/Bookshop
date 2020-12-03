package by.epam.bookshop.exceptions;

public class FactoryException extends ProjectException {
    public FactoryException(String message) {
        super(message);
    }

    public FactoryException(String message, Exception e) {
        super(message + e.getMessage());
        this.setStackTrace (e.getStackTrace());
    }
}
