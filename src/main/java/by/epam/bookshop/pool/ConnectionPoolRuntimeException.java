package by.epam.bookshop.pool;

public class ConnectionPoolRuntimeException extends RuntimeException {

    public ConnectionPoolRuntimeException(String message) {
        super(message);
    }

    public ConnectionPoolRuntimeException(String message, Exception e) {
        super(message + e.getMessage());
        this.setStackTrace (e.getStackTrace());
    }
}
