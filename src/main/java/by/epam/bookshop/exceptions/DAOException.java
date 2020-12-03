package by.epam.bookshop.exceptions;

public class DAOException extends ProjectException{
    public DAOException(String message) {
        super(message);
    }


    public DAOException(String message, Exception e) {
        super(message + e.getMessage());
        this.setStackTrace (e.getStackTrace());
    }
}
