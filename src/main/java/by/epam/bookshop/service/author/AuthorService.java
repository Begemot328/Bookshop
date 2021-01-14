package by.epam.bookshop.service.author;

import by.epam.bookshop.dao.impl.author.MySQLAuthorDAO;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.author.AuthorFactory;
import by.epam.bookshop.service.AbstractEntityService;
import by.epam.bookshop.service.EntityService;
import by.epam.bookshop.validator.impl.AuthorValidator;
import by.epam.bookshop.validator.EntityValidator;

import java.sql.Connection;

public class AuthorService extends AbstractEntityService<Author> {

    private static final AuthorService INSTANCE = new AuthorService();

    private AuthorService() {
    }

    public static EntityService<Author> getInstance() {
        return INSTANCE;
    }

    public MySQLAuthorDAO getDAO(Connection connection) {
        return new MySQLAuthorDAO(connection);
    }

    public AuthorFactory getFactory() {
        return new AuthorFactory();
    }

    @Override
    public EntityValidator<Author> getValidator() {
        return new AuthorValidator();
    }
}
