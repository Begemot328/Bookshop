package by.epam.bookshop.entity.author;

import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.exceptions.FactoryException;

public class AuthorFactory implements EntityFactory<Author> {


    @Override
    public Author create(Object... args) throws FactoryException {
        if (args.length < 2
                || !(args[0] instanceof String)
                || !(args[1] instanceof String)
                || !((args[2] instanceof String) || args[2] == null)
                || ((String) args[0]).isEmpty()
                || ((String) args[1]).isEmpty()) {
            throw new FactoryException(getWrongDataMessage());
        }
        return new Author((String) args[0], (String) args[1], (String) args[2]);
    }
}
