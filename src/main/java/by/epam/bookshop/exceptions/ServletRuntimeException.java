package by.epam.bookshop.exceptions;

import sun.rmi.runtime.Log;

public class ServletRuntimeException extends RuntimeException{

    public ServletRuntimeException(String message) {
        super(message);
    }
}
