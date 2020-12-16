package by.epam.bookshop.exceptions;

public class CommandException extends Exception{

    public CommandException() {
        super();
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Exception e) {
        super(message + e.getMessage());
        this.setStackTrace (e.getStackTrace());
    }
}
