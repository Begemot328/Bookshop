package by.epam.bookshop.entity.author;

import by.epam.bookshop.entity.user.AbstractEntityFactory;
import by.epam.bookshop.validator.impl.AuthorValidator;

import java.net.URL;

public class AuthorFactory extends AbstractEntityFactory<Author> {

    public AuthorValidator getValidator() {
        return new AuthorValidator();
    }

    @Override
    protected Author createEntity(Object... args) {

        return new Author((String) args[0], (String) args[1], (String) args[2], (URL) args[3]);
    }
}
