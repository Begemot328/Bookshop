package by.epam.bookshop.service.position_action;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.impl.book.MySQLBookDAO;
import by.epam.bookshop.dao.impl.position.MySQLPositionDAO;
import by.epam.bookshop.dao.impl.position_action.MySQLPositionActionDAO;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.book.BookFactory;
import by.epam.bookshop.entity.position_action.PositionAction;
import by.epam.bookshop.entity.position_action.PositionActionFactory;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.service.AbstractEntityService;
import by.epam.bookshop.service.EntityService;
import by.epam.bookshop.service.position.PositionService;
import by.epam.bookshop.validator.AuthorValidator;
import by.epam.bookshop.validator.EntityValidator;
import by.epam.bookshop.validator.PositionActionValidator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class PositionActionService extends AbstractEntityService<PositionAction> {

    private static final String SQL_CONNECTION_EXCEPTION = "SQL Exception: ";
    private static final String DAO_EXCEPTION = "DAO Exception: ";
    private static final String FACTORY_EXCEPTION = "User factory Exception: ";
    private static final String NO_RIGHTS_EXCEPTION
            = "Only admin or seller can manage books";
    private static final String WRONG_INPUT_EXCEPTION
            = "Wrong data input!";
    private final static PositionActionService INSTANCE = new PositionActionService();

    public static PositionActionService getInstance() {
        return INSTANCE;
    }

    public MySQLPositionActionDAO getDAO(Connection connection) {
        return new MySQLPositionActionDAO(connection);
    }

    public PositionActionFactory getFactory() {
        return new PositionActionFactory();
    }

    @Override
    public EntityValidator<PositionAction> getValidator() {
        return new PositionActionValidator();
    }
}
