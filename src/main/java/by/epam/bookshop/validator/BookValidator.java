package by.epam.bookshop.validator;

import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.validator.EntityValidator;
import by.epam.bookshop.util.ValidationUtil;

public class BookValidator implements EntityValidator<Book> {
    @Override
    public boolean validate(Object... args) {
        return !(args.length < 5
                || !(args[0] instanceof String)
                || !(args[1] instanceof Author)
                || !(args[2] instanceof String
                || args[2] == null)
                || !(args[3] instanceof Float)
                || !((args[4] instanceof String
                && ((String) args[4]).isEmpty() || ValidationUtil.validateURL((String) args[4]))
                || args[4] == null)
                || ((String) args[0]).isEmpty());
    }
}
