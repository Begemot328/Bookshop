package by.epam.bookshop.exceptions;

public class ConnectionPoolRuntimeException extends RuntimeException {

    public ConnectionPoolRuntimeException(String message) {
        super(message);
    }

    public ConnectionPoolRuntimeException(String message, Exception e) {
        super(e.getClass().getSimpleName() + ": " +  message + " " + e.getMessage());
        this.setStackTrace (e.getStackTrace());
    }

    public ConnectionPoolRuntimeException(Exception e) {
        super(e.getClass().getSimpleName() + ": " +  e.getMessage());
        this.setStackTrace (e.getStackTrace());
    }
}
