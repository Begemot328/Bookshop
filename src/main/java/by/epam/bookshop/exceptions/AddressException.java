package by.epam.bookshop.exceptions;

public class AddressException extends ProjectException {

    public AddressException(String message) {
        super(message);
    }

    public AddressException(String message, Exception e) {
        super(message, e);
    }

    public AddressException(Exception e) {
        super(e);
    }
}
