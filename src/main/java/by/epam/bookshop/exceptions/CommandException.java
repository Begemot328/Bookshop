package by.epam.bookshop.exceptions;

public class CommandException extends ProjectException{

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Exception e) {
        super(message, e);
    }

    public CommandException(Exception e) {
        super(e);
    }
}
