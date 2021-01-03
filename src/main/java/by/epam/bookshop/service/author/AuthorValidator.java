package by.epam.bookshop.service.author;

import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.service.EntityValidator;

public class AuthorValidator implements EntityValidator<Author> {
    @Override
    public boolean validate(Object... args) {
        return !(args.length < 3
                || !(args[0] instanceof String)
                || !(args[1] instanceof String)
                || !((args[2] instanceof String) || args[2] == null)
                || ((String) args[0]).isEmpty()
                || ((String) args[1]).isEmpty());
    }
}