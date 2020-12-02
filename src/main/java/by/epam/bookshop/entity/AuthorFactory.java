package by.epam.bookshop.entity;

import by.epam.bookshop.entity.factory.EntityFactory;
import by.epam.bookshop.exceptions.FactoryException;

public class AuthorFactory implements EntityFactory<Author> {

    private static final String WRONG_INPUT_DATA = "Wrong input data";

    @Override
    public Author create(Object... args) throws FactoryException {
        if (args.length < 2
                || !(args[0] instanceof String)
                || !(args[1] instanceof String)
                || ((String) args[0]).isEmpty()
                || ((String) args[1]).isEmpty()) {
            throw new FactoryException(getWrongDataMessage());
        }
        return new Author((String) args[0], (String) args[1]);
    }
}
