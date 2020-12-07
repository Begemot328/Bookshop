package by.epam.bookshop.exceptions;

public class ServiceException extends Exception{

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Exception e) {
        super(message + e.getMessage());
        this.setStackTrace (e.getStackTrace());
    }
}
