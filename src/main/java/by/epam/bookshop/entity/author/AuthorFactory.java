package by.epam.bookshop.entity.author;

import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.user.AbstractEntityFactory;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.validator.AuthorValidator;
import by.epam.bookshop.validator.EntityValidator;

import java.net.URL;

public class AuthorFactory extends AbstractEntityFactory<Author> {

    public AuthorValidator getValidator() {
        return new AuthorValidator();
    }

    @Override
    protected Author createEntity(Object... args) {

        return new Author((String) args[0], (String) args[1], (URL) args[2]);
    }
}
