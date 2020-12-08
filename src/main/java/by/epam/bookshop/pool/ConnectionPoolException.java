package by.epam.bookshop.pool;

/**
 * The Class ConnectionPoolException.
 */
public class ConnectionPoolException extends Exception {
    
    /**
     * Instantiates a new connection pool exception.
     *
     * @param message the message
     * @param e the e
     */
    public ConnectionPoolException(String message, Exception e){
        super(message, e);
    }
}
